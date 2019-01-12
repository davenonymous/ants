package org.dave.ants.hills;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.dave.ants.Ants;
import org.dave.ants.actions.BuyChamber;
import org.dave.ants.api.actions.IAntGuiAction;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.hill.IHillData;
import org.dave.ants.api.properties.IHillProperty;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.util.serialization.Store;
import org.dave.ants.base.BaseNBTSerializable;
import org.dave.ants.tiles.BaseHillTile;
import org.dave.ants.util.DimPos;
import org.dave.ants.util.Logz;
import org.dave.ants.util.serialization.FieldHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HillData extends BaseNBTSerializable implements IHillData {
    @Store
    public int hillId = -1;

    // TODO: Add randomized hill name feature

    @Store
    public Map<DimPos, IAntChamber> chambers = new HashMap<>();

    @Store
    public Map<Class<? extends IHillProperty>, IHillProperty> properties = new HashMap<>();

    // These are cached/calculate values and are being dynamically calculated from the chambers
    public Map<Class<? extends IHillProperty>, IHillProperty> calculatedProperties = new HashMap<>();

    public Map<Class<? extends IAntChamber>, Integer> maxTierLevels = new HashMap<>();
    public List<DimPos> tickingHillChambers = new ArrayList<>();
    private boolean cacheOutdated = true;

    // Other values needed to track some things for various stuff
    private long currentWorldTick;

    @Override
    public void afterLoad() {
        cacheOutdated = true;
    }

    @Override
    public <V> IHillProperty<V> getProperty(Class<? extends IHillProperty<V>> propClass) {
        Map<Class<? extends IHillProperty>, IHillProperty> mapToUse = this.calculatedProperties;
        if(Ants.hillProperties.shouldStoreProperty(propClass)) {
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

    @Override
    public <V> V getPropertyValue(Class<? extends IHillProperty<V>> property) {
        return getProperty(property).getValue();
    }

    @Override
    public <V> void setPropertyValue(Class<? extends IHillProperty<V>> property, V value) {
        IHillProperty<V> prop = getProperty(property);
        V toSet = prop.clamp(value);
        prop.setValue(toSet);
        this.markDirty();
    }

    @Override
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

    public void onHillAction(EntityPlayerMP player, IAntGuiAction action) {
        if(action instanceof BuyChamber) {
            BuyChamber buyChamberAction = (BuyChamber)action;

            IAntChamber chamber = Ants.chamberTypes.getChamberInstance(buyChamberAction.type);
            if(chamber == null) {
                Logz.warn("Player '%s' tried to buy a chamber that does not exist (chamber=%s)!", buyChamberAction.type.getName());
                return;
            }

            int tier = maxTierLevels.getOrDefault(buyChamberAction.type, 0);

            if(chamber.getTierList().size() <= tier || tier < 0) {
                Logz.warn("Player '%s' tried to buy a tier level that does not exist (tier=%d, chamber=%s)!", tier, buyChamberAction.type.getName());
                return;
            }

            double cost = chamber.tierCost(tier, chamber.getTierList().get(tier));
            if(getPropertyValue(TotalAnts.class) < cost) {
                // TODO: Notify player that he does not have enough ants
                return;
            }

            modifyPropertyValue(TotalAnts.class, ants -> ants - cost);

            ItemStack stack = Ants.chamberTypes.createItemStackForChamberType(buyChamberAction.type);
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

    @SuppressWarnings("unchecked")
    private void resetValues() {
        for(Class propClass : Ants.hillProperties.getHillProperties()) {
            IHillProperty property = getProperty(propClass);

            // Do not overwrite stored properties, they are not being dynamically calculated
            if(Ants.hillProperties.shouldStoreProperty(propClass)) {
                continue;
            }

            property.setValue(property.getDefault());
        }

        tickingHillChambers.clear();
        maxTierLevels.clear();
    }


    @SuppressWarnings("unchecked")
    public NBTTagCompound getPropertiesTag() {
        NBTTagCompound result = new NBTTagCompound();
        FieldHandlers.getNBTHandler(properties.getClass()).getRight().write("properties", properties, result);
        FieldHandlers.getNBTHandler(calculatedProperties.getClass()).getRight().write("calculated", calculatedProperties, result);
        return result;
    }


    @SuppressWarnings("unchecked")
    public NBTTagCompound getMaxTierLevelsTag() {
        NBTTagCompound result = new NBTTagCompound();
        FieldHandlers.getNBTHandler(maxTierLevels.getClass()).getRight().write("maxTierLevels", maxTierLevels, result);
        return result;
    }

    @Override
    public void updateHillStatistics() {
        this.resetValues();

        for(Map.Entry<DimPos, IAntChamber> chamberEntry : chambers.entrySet()) {
            IAntChamber chamber = chamberEntry.getValue();
            int chamberTier = 0;
            BaseHillTile tile = chamberEntry.getKey().getTileEntity(BaseHillTile.class);
            if(tile != null) {
                chamberTier = tile.getChamberTier();
            }

            chamber.applyHillModification(this, chamberTier);
            if(chamber.shouldTick()) {
                tickingHillChambers.add(chamberEntry.getKey());
            }

            // Also build up a mapping between chamber -> max level, so we can easily access that info without looping over all blocks
            int finalChamberTier = chamberTier;
            maxTierLevels.compute(chamber.getClass(), (aClass, integer) -> integer == null ? finalChamberTier : Math.max(integer, finalChamberTier));
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

        Ants.calculations.performCalculations(this, currentWorldTick);

        for(DimPos pos : tickingHillChambers) {
            chambers.get(pos).tickPostCalc(this, pos.getWorld(), pos.getBlockPos());
        }
    }
}
