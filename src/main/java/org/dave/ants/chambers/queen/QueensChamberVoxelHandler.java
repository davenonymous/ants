package org.dave.ants.chambers.queen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.api.render.VoxelSpaceTools;

@AntChamberVoxelHandler(antChamberType = QueensChamber.class)
public class QueensChamberVoxelHandler implements IAntChamberVoxelHandler {
    @Override
    public void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        int xOffset = 5;
        int zOffset = 5;

        // Dig a little bit into the block, our egg is too high
        int y = voxels.flattenArea(xOffset, zOffset, 4, 4, Blocks.DIRT.getDefaultState()) -2;
        VoxelSpaceTools.block(voxels, xOffset, y, zOffset, 4, 3, 4, Blocks.AIR.getDefaultState(), false, false);

        @SuppressWarnings("deprecation") IBlockState eggShell = Blocks.QUARTZ_BLOCK.getStateFromMeta(1);

        y--;
        VoxelSpaceTools.hollowBlock(voxels, xOffset+2, y, zOffset+2, 2, 1, 2, eggShell);

        VoxelSpaceTools.hollowBlock(voxels, xOffset+1, y+1, zOffset+1, 4, 6, 4, eggShell);

        VoxelSpaceTools.hollowBlock(voxels, xOffset, y+2, zOffset+2, 6, 2, 2, eggShell);
        VoxelSpaceTools.hollowBlock(voxels, xOffset+2, y+2, zOffset, 2, 2, 6, eggShell);

        voxels.removeVoxel(xOffset+1, y+6, zOffset+1);
        voxels.removeVoxel(xOffset+4, y+6, zOffset+1);
        voxels.removeVoxel(xOffset+1, y+6, zOffset+4);
        voxels.removeVoxel(xOffset+4, y+6, zOffset+4);

        VoxelSpaceTools.hollowBlock(voxels, xOffset+2, y+7, zOffset+2, 2, 1, 2, eggShell);
    }
}
