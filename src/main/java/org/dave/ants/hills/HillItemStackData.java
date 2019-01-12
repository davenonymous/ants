package org.dave.ants.hills;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.serialization.Store;
import org.dave.ants.base.BaseNBTSerializable;

import javax.annotation.Nullable;

public class HillItemStackData extends BaseNBTSerializable {
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

    public boolean hasChamberData() {
        return chamberData != null;
    }

    public NBTTagCompound getChamberData() {
        return chamberData;
    }

    public int getHillId() {
        return hillId;
    }

    public boolean hasHillId() {
        return hillId != -1;
    }

    public int getChamberTier() {
        return chamberTier;
    }

    @Nullable
    public Class<? extends IAntChamber> getChamberType() {
        return chamberType;
    }

    public boolean hasChamberType() {
        return chamberType != null;
    }
}
