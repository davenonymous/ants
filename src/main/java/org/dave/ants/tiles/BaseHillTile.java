package org.dave.ants.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.serialization.Store;
import org.dave.ants.base.BaseTileEntity;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.DimPos;

public abstract class BaseHillTile extends BaseTileEntity {
    @Store(sendInUpdatePackage = true, storeWithItem = true)
    private int hillId;

    public int getHillId() {
        return hillId;
    }

    public void setHillId(int hillId) {
        this.hillId = hillId;
        this.markDirty();
    }

    public HillData getHillData() {
        return Ants.savedData.hills.getHillData(this.getHillId());
    }

    public abstract Class<? extends IAntChamber> getChamberType();

    @Override
    protected NBTTagCompound createItemStackTagCompound() {
        NBTTagCompound result = super.createItemStackTagCompound();

        // We might need to add chamber data
        if(Ants.savedData == null || Ants.savedData.hills == null || getHillData() == null) {
            return result;
        }

        IAntChamber chamber = getHillData().chambers.get(new DimPos(this.world, this.pos));
        if(chamber != null) {
            result.setTag("chamber", chamber.serializeNBT());
        }

        return result;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        // TODO: Reinvestigate when shouldRefresh should return true
        return true;
    }
}
