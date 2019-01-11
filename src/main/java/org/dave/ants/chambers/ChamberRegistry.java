package org.dave.ants.chambers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.api.chambers.IAntChamber;
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

public class ChamberRegistry {
    private static Set<Class<? extends IAntChamber>> chamberTypes;

    public static void findChamberIntegrations() {
        chamberTypes = new HashSet<>();

        List<String> disabledChambers = Arrays.asList(GeneralAntHillConfig.disabledChambers);
        for(Class<? extends IAntChamber> clz : AnnotatedInstanceUtil.getAntChamberClasses()) {
            if(disabledChambers.contains(clz.getName())) {
                continue;
            }

            chamberTypes.add(clz);
        }
    }

    public static ItemStack createItemStackForChamberType(Class<? extends IAntChamber> chamberType) {
        ItemStack stack = new ItemStack(Blockss.chamber, 1, 0);
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("chamberType", chamberType.getName());
        stack.setTagCompound(tagCompound);

        return stack;
    }

    @Nullable
    public static IAntChamber getChamberInstance(@Nonnull HillItemStackData data) {
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

    @Nullable
    public static IAntChamber getChamberInstance(Class<? extends IAntChamber> type) {
        return getChamberInstance(type, null);
    }

    @Nullable
    public static IAntChamber getChamberInstance(Class<? extends IAntChamber> type, @Nullable HillItemStackData data) {
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


    // TODO: Add a way to register new chambers without annotations, so e.g. crafttweaker can do stuff

    public static Set<Class<? extends IAntChamber>> getChamberTypes() {
        return chamberTypes;
    }
}
