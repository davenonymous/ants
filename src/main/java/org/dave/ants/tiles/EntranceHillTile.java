package org.dave.ants.tiles;

import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.chambers.entrance.EntranceChamber;

public class EntranceHillTile extends BaseHillTile {
    public EntranceHillTile() {
    }

    @Override
    public void update() {
        super.update();
        if(world.isRemote) {
            return;
        }

        this.getHillData().tick(world);
    }

    @Override
    public Class<? extends IAntChamber> getChamberType() {
        return EntranceChamber.class;
    }
}
