package org.dave.ants.tiles;

import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.serialization.Store;

public class ChamberHillTile extends BaseHillTile {
    @Store(sendInUpdatePackage = true, storeWithItem = true)
    public Class<? extends IAntChamber> chamberType;

    public Class<? extends IAntChamber> getChamberType() {
        return chamberType;
    }

    public void setChamberType(Class<? extends IAntChamber> chamberType) {
        this.chamberType = chamberType;
        this.markDirty();
    }

    @Override
    public boolean renderUpdateOnDataPacket() {
        return true;
    }

    @Override
    public void update() {
        super.update();
    }
}
