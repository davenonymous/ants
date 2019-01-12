package org.dave.ants.hills;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.api.IAntStackData;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.util.serialization.Store;
import org.dave.ants.base.BaseNBTSerializable;

import javax.annotation.Nullable;

public class HillItemStackData extends BaseNBTSerializable implements IAntStackData {
    @Store
    private int hillId = -1;

    @Store
    private Class<? extends IAntChamber> chamberType;

    @Store
    private int chamberTier;

    private NBTTagCompound chamberData;

    public HillItemStackData(ItemStack hillStack) {
        if(hillStack.hasTagCompound()) {
            deserializeNBT(hillStack.getTagCompound());

            // BaseNBTSerializable deserializes to 0 if no value is present, but we need -1 here.
            if(!hillStack.getTagCompound().hasKey("hillId")) {
                this.hillId = -1;
            }

            if(hillStack.getTagCompound().hasKey("chamber")) {
                chamberData = hillStack.getTagCompound().getCompoundTag("chamber");
            }
        }
    }

    @Override
    public boolean hasChamberData() {
        return chamberData != null;
    }

    @Override
    public NBTTagCompound getChamberData() {
        return chamberData;
    }

    @Override
    public int getHillId() {
        return hillId;
    }

    @Override
    public boolean hasHillId() {
        return hillId != -1;
    }

    @Override
    public int getChamberTier() {
        return chamberTier;
    }

    @Override
    @Nullable
    public Class<? extends IAntChamber> getChamberType() {
        return chamberType;
    }

    @Override
    public boolean hasChamberType() {
        return chamberType != null;
    }
}
