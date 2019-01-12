package org.dave.ants.tiles;

import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.util.serialization.Store;

public class ChamberHillTile extends BaseHillTile {
    @Store(sendInUpdatePackage = true, storeWithItem = true)
    public Class<? extends IAntChamber> chamberType;

    @Store(sendInUpdatePackage = true, storeWithItem = true)
    public int chamberTier;

    @Override
    public Class<? extends IAntChamber> getChamberType() {
        return chamberType;
    }

    public void setChamberType(Class<? extends IAntChamber> chamberType) {
        this.chamberType = chamberType;
        this.markDirty();
    }

    @Override
    public int getChamberTier() {
        return chamberTier;
    }

    public ChamberHillTile setChamberTier(int chamberTier) {
        this.chamberTier = chamberTier;
        return this;
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
