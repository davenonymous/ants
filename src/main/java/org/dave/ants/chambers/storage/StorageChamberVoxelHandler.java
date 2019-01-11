package org.dave.ants.chambers.storage;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.render.VoxelSpaceTools;

@AntChamberVoxelHandler(antChamberType = StorageChamber.class)
public class StorageChamberVoxelHandler implements IAntChamberVoxelHandler {
    private void fallApple(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        IBlockState color_000 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.BROWN.getMetadata()); // #77340D
        IBlockState color_001 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.BLACK.getMetadata()); // #54090E
        IBlockState color_002 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.RED.getMetadata()); // #FF1C2B
        IBlockState color_003 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.PINK.getMetadata()); // #FFAAAF

        int xOffset = 0;
        int yOffset = 0;
        voxels.fallVoxel(xOffset + 2, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 2, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 2, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 2, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 6, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 3, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 3, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 3, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 3, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 13, color_001);
        voxels.fallVoxel(xOffset + 4, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 4, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 8, color_003);
        voxels.fallVoxel(xOffset + 4, yOffset + 9, color_003);
        voxels.fallVoxel(xOffset + 4, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 4, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 7, color_003);
        voxels.fallVoxel(xOffset + 5, yOffset + 8, color_003);
        voxels.fallVoxel(xOffset + 5, yOffset + 9, color_003);
        voxels.fallVoxel(xOffset + 5, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 5, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 5, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 7, color_003);
        voxels.fallVoxel(xOffset + 6, yOffset + 8, color_003);
        voxels.fallVoxel(xOffset + 6, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 6, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 5, color_000);
        voxels.fallVoxel(xOffset + 7, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 7, color_003);
        voxels.fallVoxel(xOffset + 7, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 7, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 2, color_000);
        voxels.fallVoxel(xOffset + 8, yOffset + 3, color_000);
        voxels.fallVoxel(xOffset + 8, yOffset + 4, color_000);
        voxels.fallVoxel(xOffset + 8, yOffset + 5, color_000);
        voxels.fallVoxel(xOffset + 8, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 8, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 1, color_000);
        voxels.fallVoxel(xOffset + 9, yOffset + 2, color_000);
        voxels.fallVoxel(xOffset + 9, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 9, yOffset + 5, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 13, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 11, yOffset + 6, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 7, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 12, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 13, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 6, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 8, color_002);
        voxels.fallVoxel(xOffset + 12, yOffset + 9, color_002);
        voxels.fallVoxel(xOffset + 12, yOffset + 10, color_002);
        voxels.fallVoxel(xOffset + 12, yOffset + 11, color_002);
        voxels.fallVoxel(xOffset + 12, yOffset + 12, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 13, color_001);
        voxels.fallVoxel(xOffset + 13, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 13, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 13, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 13, yOffset + 11, color_001);
    }

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
