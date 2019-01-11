package org.dave.ants.hills;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.actions.BuyChamber;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IChamberAction;
import org.dave.ants.api.hill.IHillProperty;
import org.dave.ants.api.properties.calculated.*;
import org.dave.ants.api.properties.stored.LastAntBornTick;
import org.dave.ants.api.properties.stored.StoredFood;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.api.serialization.Store;
import org.dave.ants.base.BaseNBTSerializable;
import org.dave.ants.chambers.ChamberRegistry;
import org.dave.ants.util.DimPos;
import org.dave.ants.util.serialization.FieldHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HillData extends BaseNBTSerializable {
    @Store
    public int hillId = -1;

    // TODO: Add randomized hill name feature

    @Store
    public Map<DimPos, IAntChamber> chambers = new HashMap<>();

    @Store
    public Map<Class<? extends IHillProperty>, IHillProperty> properties = new HashMap<>();

    // These are cached/calculate values and are being dynamically calculated from the chambers
    public Map<Class<? extends IHillProperty>, IHillProperty> calculatedProperties = new HashMap<>();

    public List<DimPos> tickingHillChambers = new ArrayList<>();
    private boolean cacheOutdated = true;

    // Other values needed to track some things for various stuff
    private long currentWorldTick;

    @Override
    public void afterLoad() {
        cacheOutdated = true;
    }

    public <V> IHillProperty<V> getProperty(Class<? extends IHillProperty<V>> propClass) {
        Map<Class<? extends IHillProperty>, IHillProperty> mapToUse = this.calculatedProperties;
        if(HillPropertyRegistry.shouldStoreProperty(propClass)) {
            mapToUse = this.properties;
        }

        if(!mapToUse.containsKey(propClass)) {
            try {
                IHillProperty property = propClass.newInstance();
                property.setValue(property.getDefault());
                mapToUse.put(propClass, property);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return mapToUse.get(propClass);
    }

    public <V> V getPropertyValue(Class<? extends IHillProperty<V>> property) {
        return getProperty(property).getValue();
    }

    public <V> void setPropertyValue(Class<? extends IHillProperty<V>> property, V value) {
        IHillProperty<V> prop = getProperty(property);
        V toSet = prop.clamp(value);
        prop.setValue(toSet);
        this.markDirty();
    }

    public <V> void modifyPropertyValue(Class<? extends IHillProperty<V>> property, Function<V, V> func) {
        IHillProperty<V> prop = getProperty(property);
        V newValue;
        if(prop.getValue() != null) {
            newValue = func.apply(prop.getValue());
        } else {
            newValue = func.apply(prop.getDefault());
        }

        V toSet = prop.clamp(newValue);
        prop.setValue(toSet);
        this.markDirty();
    }

    public void onHillAction(EntityPlayerMP player, IChamberAction action) {
        if(action instanceof BuyChamber) {
            BuyChamber buyChamberAction = (BuyChamber)action;

            IAntChamber chamber = ChamberRegistry.getChamberInstance(buyChamberAction.type);
            boolean boughtSuccessfully = chamber.payPrice(this);

            if(!boughtSuccessfully) {
                return;
            }

            ItemStack stack = ChamberRegistry.createItemStackForChamberType(buyChamberAction.type);
            if(!player.addItemStackToInventory(stack)) {
                EntityItem entityItem = new EntityItem(player.world, player.posX, player.posY + 0.5f, player.posZ, stack);
                entityItem.lifespan = 1200;
                entityItem.setPickupDelay(5);

                entityItem.motionX = 0.0f;
                entityItem.motionY = 0.10f;
                entityItem.motionZ = 0.0f;

                player.world.spawnEntity(entityItem);
            }
        }
    }

    private void resetValues() {
        for(Class propClass : HillPropertyRegistry.getHillProperties()) {
            IHillProperty property = getProperty(propClass);

            // Do not overwrite stored properties, they are not being dynamically calculated
            if(HillPropertyRegistry.shouldStoreProperty(propClass)) {
                continue;
            }

            property.setValue(property.getDefault());
        }

        tickingHillChambers.clear();
    }

    public NBTTagCompound getPropertiesTag() {
        NBTTagCompound result = new NBTTagCompound();
        FieldHandlers.getNBTHandler(properties.getClass()).getRight().write("properties", properties, result);
        FieldHandlers.getNBTHandler(calculatedProperties.getClass()).getRight().write("calculated", calculatedProperties, result);
        return result;
    }

    public void updateHillStatistics() {
        this.resetValues();

        for(Map.Entry<DimPos, IAntChamber> chamberEntry : chambers.entrySet()) {
            IAntChamber chamber = chamberEntry.getValue();
            chamber.applyHillModification(this);
            if(chamber.shouldTick()) {
                tickingHillChambers.add(chamberEntry.getKey());
            }
        }

        cacheOutdated = false;
        this.markDirty();
    }


    // Game-Logic
    public void tick(World world) {
        currentWorldTick = world.getTotalWorldTime();
        if(cacheOutdated) {
            updateHillStatistics();
        }

        for(DimPos pos : tickingHillChambers) {
            chambers.get(pos).tickPreCalc(this, pos.getWorld(), pos.getBlockPos());
        }

        // TODO: Move to IGameLogicCalculation or something api foobar
        generateFood();
        createNewAnts();
        eatFood();

        for(DimPos pos : tickingHillChambers) {
            chambers.get(pos).tickPostCalc(this, pos.getWorld(), pos.getBlockPos());
        }
    }

    private void generateFood() {
        double storedFood = getPropertyValue(StoredFood.class);
        double foodGainPerTick = getPropertyValue(FoodGainPerTick.class);
        double maxCapacity = getPropertyValue(FoodCapacity.class);

        setPropertyValue(StoredFood.class, Math.min(storedFood + foodGainPerTick, maxCapacity));
    }

    private void createNewAnts() {
        double totalAnts = getPropertyValue(TotalAnts.class);
        double maxAnts = getPropertyValue(MaxAnts.class);

        // Don't bear new babies if there is no more room
        if(totalAnts >= maxAnts) {
            return;
        }

        // Check if its time already
        if(getPropertyValue(LastAntBornTick.class) + getPropertyValue(TicksBetweenBabies.class) >= currentWorldTick) {
            return;
        }

        double requiredFood = 1000 * getPropertyValue(FoodRequirementPerAnt.class);
        if(getPropertyValue(StoredFood.class) < requiredFood) {
            return;
        }

        // Create one new ant per queen, but limit to maxAnts
        long totalQueens = getPropertyValue(TotalQueens.class);


        setPropertyValue(TotalAnts.class, Math.min(totalAnts + totalQueens, maxAnts));

        // Remember when we last created ants
        setPropertyValue(LastAntBornTick.class, currentWorldTick);

        // It costs food to create new ants
        modifyPropertyValue(StoredFood.class, storedFood -> storedFood - (storedFood * 0.1d));
    }

    private void eatFood() {
        double totalAnts = getPropertyValue(TotalAnts.class);
        double requirementPerAnt = getPropertyValue(FoodRequirementPerAnt.class);

        double requiredFood = totalAnts * requirementPerAnt;

        double storedFood = getPropertyValue(StoredFood.class);
        double remainingFood = storedFood - requiredFood;
        setPropertyValue(StoredFood.class, Math.max(remainingFood, 0.0d));

        if(remainingFood < 0) {
            modifyPropertyValue(TotalAnts.class, ants -> Math.max(ants - 1, 0d));
        }
    }
}
