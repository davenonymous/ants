package org.dave.ants.chambers.queen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.render.VoxelSpaceTools;

@AntChamberVoxelHandler(antChamberType = QueensChamber.class)
public class QueensChamberVoxelHandler implements IAntChamberVoxelHandler {
    private void fallSpawnEgg(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        IBlockState color_000 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.BLACK.getMetadata()); // #5F5F5F
        IBlockState color_001 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.RED.getMetadata()); // #A70A0A
        IBlockState color_002 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.WHITE.getMetadata()); // #F6F6F6

        int xOffset = 0;
        int yOffset = 0;
        voxels.fallVoxel(xOffset + 2, yOffset + 8, color_000);
        voxels.fallVoxel(xOffset + 2, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 2, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 6, color_000);
        voxels.fallVoxel(xOffset + 3, yOffset + 7, color_000);
        voxels.fallVoxel(xOffset + 3, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 3, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 11, color_000);
        voxels.fallVoxel(xOffset + 3, yOffset + 12, color_000);
        voxels.fallVoxel(xOffset + 4, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 4, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 4, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 13, color_000);
        voxels.fallVoxel(xOffset + 5, yOffset + 3, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 6, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 13, color_000);
        voxels.fallVoxel(xOffset + 6, yOffset + 3, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 6, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 2, color_000);
        voxels.fallVoxel(xOffset + 7, yOffset + 3, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 2, color_000);
        voxels.fallVoxel(xOffset + 8, yOffset + 3, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 4, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 5, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 8, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 8, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 3, color_000);
        voxels.fallVoxel(xOffset + 9, yOffset + 4, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 5, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 9, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 9, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 3, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 13, color_000);
        voxels.fallVoxel(xOffset + 11, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 11, yOffset + 5, color_000);
        voxels.fallVoxel(xOffset + 11, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 11, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 11, yOffset + 13, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 6, color_000);
        voxels.fallVoxel(xOffset + 12, yOffset + 7, color_000);
        voxels.fallVoxel(xOffset + 12, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 12, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 12, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 13, yOffset + 8, color_000);
        voxels.fallVoxel(xOffset + 13, yOffset + 9, color_000);
        voxels.fallVoxel(xOffset + 13, yOffset + 10, color_000);

    }

    @Override
    public void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        int xOffset = 5;
        int zOffset = 5;

        // Dig a little bit into the block, our egg is too high
        int y = voxels.flattenArea(xOffset, zOffset, 4, 4, Blocks.DIRT.getDefaultState()) -2;
        VoxelSpaceTools.block(voxels, xOffset, y, zOffset, 4, 3, 4, Blocks.AIR.getDefaultState(), false, false);

        IBlockState eggShell = Blocks.QUARTZ_BLOCK.getStateFromMeta(1);

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
