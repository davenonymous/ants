package org.dave.ants.chambers.storage;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.render.VoxelSpaceTools;

@AntChamberVoxelHandler(antChamberType = StorageChamber.class)
public class StorageChamberVoxelHandler implements IAntChamberVoxelHandler {
    @SuppressWarnings("deprecation")
    @Override
    public void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        int xOffset = 5;
        int zOffset = 5;

        int width = 6;
        int depth = 6;
        int height = 6;

        IBlockState dirt = Blocks.DIRT.getDefaultState();
        IBlockState oak = Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.OAK.getMetadata());
        IBlockState darkOak = Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.DARK_OAK.getMetadata());

        int y = voxels.flattenArea(xOffset, zOffset, width, depth, dirt);
        if(y+height > voxels.getDimension()) {
            y = voxels.getDimension()-height;
        }

        VoxelSpaceTools.hollowBlock(voxels, xOffset, y, zOffset, width, height, depth, oak);
        VoxelSpaceTools.blockFrame(voxels, xOffset, y, zOffset, width, height, depth, darkOak);
        VoxelSpaceTools.blockFrame(voxels, xOffset, y+3, zOffset, width, 1, depth, darkOak);
    }
}
