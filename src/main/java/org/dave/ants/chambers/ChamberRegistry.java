package org.dave.ants.chambers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.api.IAntStackData;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IChamberRegistry;
import org.dave.ants.config.GeneralAntHillConfig;
import org.dave.ants.hills.HillItemStackData;
import org.dave.ants.init.Blockss;
import org.dave.ants.util.AnnotatedInstanceUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChamberRegistry implements IChamberRegistry {
    private Set<Class<? extends IAntChamber>> chamberTypes;

    public void findChamberIntegrations() {
        chamberTypes = new HashSet<>();

        List<String> disabledChambers = Arrays.asList(GeneralAntHillConfig.disabledChambers);
        for(Class<? extends IAntChamber> clz : AnnotatedInstanceUtil.getAntChamberClasses()) {
            if(disabledChambers.contains(clz.getName())) {
                continue;
            }

            chamberTypes.add(clz);
        }
    }

    @Override
    public void registerChamberType(Class<? extends IAntChamber> chamberType) {
        if(Arrays.asList(GeneralAntHillConfig.disabledChambers).contains(chamberType.getName())) {
            return;
        }

        chamberTypes.add(chamberType);
    }

    @Override
    public ItemStack createItemStackForChamberType(Class<? extends IAntChamber> chamberType, int tier) {
        ItemStack stack = new ItemStack(Blockss.chamber, 1, 0);
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("chamberType", chamberType.getName());
        tagCompound.setInteger("chamberTier", tier);
        stack.setTagCompound(tagCompound);

        return stack;
    }

    @Override
    @Nullable
    public IAntChamber getChamberInstance(@Nonnull IAntStackData data) {
        try {
            IAntChamber result = data.getChamberType().newInstance();
            if(data.hasChamberData()) {
                result.deserializeNBT(data.getChamberData());
            }

            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @Nullable
    public IAntChamber getChamberInstance(Class<? extends IAntChamber> type) {
        return getChamberInstance(type, null);
    }

    @Override
    @Nullable
    public IAntChamber getChamberInstance(Class<? extends IAntChamber> type, @Nullable IAntStackData data) {
        try {
            IAntChamber result = type.newInstance();
            if(data != null && data.hasChamberData()) {
                result.deserializeNBT(data.getChamberData());
            }

            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Set<Class<? extends IAntChamber>> getChamberTypes() {
        return chamberTypes;
    }
}
