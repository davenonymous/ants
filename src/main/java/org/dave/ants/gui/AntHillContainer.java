package org.dave.ants.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.blocks.BaseHillBlock;
import org.dave.ants.hills.HillData;
import org.dave.ants.network.AntsNetworkHandler;
import org.dave.ants.network.ChamberDataMessage;
import org.dave.ants.tiles.BaseHillTile;
import org.dave.ants.util.DimPos;
import org.dave.ants.util.Logz;

public class AntHillContainer extends Container {
    World world;
    BlockPos pos;

    public AntHillContainer(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if(this.world.isRemote || this.world.getTotalWorldTime() % 20 != 0) {
            return;
        }

        BaseHillTile hillTile = BaseHillBlock.getHillBaseTileEntity(world, pos);
        if(hillTile == null) {
            return;
        }

        HillData hillData = Ants.savedData.hills.getHillData(hillTile.getHillId());
        if(hillData == null) {
            return;
        }

        IAntChamber liveChamberData = hillData.chambers.get(new DimPos(world, pos));
        if(liveChamberData == null) {
            return;
        }

        for(IContainerListener listener : this.listeners) {
            if(!(listener instanceof EntityPlayerMP)) {
                continue;
            }

            AntsNetworkHandler.instance.sendTo(new ChamberDataMessage(hillTile.getChamberType(), liveChamberData.serializeNBT(), hillData.getPropertiesTag(), hillData.getMaxTierLevelsTag(), new DimPos(world, pos)), (EntityPlayerMP) listener);
        }
    }
}
