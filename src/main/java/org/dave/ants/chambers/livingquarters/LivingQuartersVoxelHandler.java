package org.dave.ants.chambers.livingquarters;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.render.VoxelSpaceTools;

@AntChamberVoxelHandler(antChamberType = LivingQuarters.class)
public class LivingQuartersVoxelHandler implements IAntChamberVoxelHandler {
    private void fallBed(IVoxelSpace voxels) {
        IBlockState color_000 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.GRAY.getMetadata()); // #727272
        IBlockState color_001 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.RED.getMetadata()); // #720000
        IBlockState color_002 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.WHITE.getMetadata()); // #BEBEBE
        IBlockState color_003 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.WHITE.getMetadata()); // #EBEBEB
        IBlockState color_004 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.RED.getMetadata()); // #9C2626
        IBlockState color_005 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.RED.getMetadata()); // #8C1515
        IBlockState color_006 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.RED.getMetadata()); // #810707
        IBlockState color_007 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.BROWN.getMetadata()); // #413421
        IBlockState color_008 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.BROWN.getMetadata()); // #695433
        IBlockState color_009 = Blocks.WOOL.getStateFromMeta(EnumDyeColor.GRAY.getMetadata()); // #9F844D

        int xOffset = 0;
        int yOffset = 0;
        voxels.fallVoxel(xOffset + 1, yOffset + 6, color_001);
        voxels.fallVoxel(xOffset + 1, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 1, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 1, yOffset + 9, color_007);
        voxels.fallVoxel(xOffset + 1, yOffset + 10, color_008);
        voxels.fallVoxel(xOffset + 2, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 2, yOffset + 6, color_005);
        voxels.fallVoxel(xOffset + 2, yOffset + 7, color_005);
        voxels.fallVoxel(xOffset + 2, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 2, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 2, yOffset + 10, color_007);
        voxels.fallVoxel(xOffset + 3, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 5, color_005);
        voxels.fallVoxel(xOffset + 3, yOffset + 6, color_004);
        voxels.fallVoxel(xOffset + 3, yOffset + 7, color_004);
        voxels.fallVoxel(xOffset + 3, yOffset + 8, color_005);
        voxels.fallVoxel(xOffset + 3, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 3, yOffset + 11, color_007);
        voxels.fallVoxel(xOffset + 4, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 4, yOffset + 5, color_005);
        voxels.fallVoxel(xOffset + 4, yOffset + 6, color_004);
        voxels.fallVoxel(xOffset + 4, yOffset + 7, color_005);
        voxels.fallVoxel(xOffset + 4, yOffset + 8, color_005);
        voxels.fallVoxel(xOffset + 4, yOffset + 9, color_005);
        voxels.fallVoxel(xOffset + 4, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 4, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 4, yOffset + 12, color_009);
        voxels.fallVoxel(xOffset + 4, yOffset + 13, color_008);
        voxels.fallVoxel(xOffset + 4, yOffset + 14, color_007);
        voxels.fallVoxel(xOffset + 5, yOffset + 3, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 4, color_005);
        voxels.fallVoxel(xOffset + 5, yOffset + 5, color_004);
        voxels.fallVoxel(xOffset + 5, yOffset + 6, color_005);
        voxels.fallVoxel(xOffset + 5, yOffset + 7, color_005);
        voxels.fallVoxel(xOffset + 5, yOffset + 8, color_005);
        voxels.fallVoxel(xOffset + 5, yOffset + 9, color_006);
        voxels.fallVoxel(xOffset + 5, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 11, color_001);
        voxels.fallVoxel(xOffset + 5, yOffset + 12, color_007);
        voxels.fallVoxel(xOffset + 5, yOffset + 13, color_007);
        voxels.fallVoxel(xOffset + 5, yOffset + 14, color_007);
        voxels.fallVoxel(xOffset + 6, yOffset + 3, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 4, color_005);
        voxels.fallVoxel(xOffset + 6, yOffset + 5, color_005);
        voxels.fallVoxel(xOffset + 6, yOffset + 6, color_005);
        voxels.fallVoxel(xOffset + 6, yOffset + 7, color_005);
        voxels.fallVoxel(xOffset + 6, yOffset + 8, color_005);
        voxels.fallVoxel(xOffset + 6, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 6, yOffset + 11, color_007);
        voxels.fallVoxel(xOffset + 7, yOffset + 2, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 3, color_004);
        voxels.fallVoxel(xOffset + 7, yOffset + 4, color_005);
        voxels.fallVoxel(xOffset + 7, yOffset + 5, color_005);
        voxels.fallVoxel(xOffset + 7, yOffset + 6, color_005);
        voxels.fallVoxel(xOffset + 7, yOffset + 7, color_005);
        voxels.fallVoxel(xOffset + 7, yOffset + 8, color_006);
        voxels.fallVoxel(xOffset + 7, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 10, color_001);
        voxels.fallVoxel(xOffset + 7, yOffset + 11, color_007);
        voxels.fallVoxel(xOffset + 8, yOffset + 2, color_001);
        voxels.fallVoxel(xOffset + 8, yOffset + 3, color_004);
        voxels.fallVoxel(xOffset + 8, yOffset + 4, color_004);
        voxels.fallVoxel(xOffset + 8, yOffset + 5, color_005);
        voxels.fallVoxel(xOffset + 8, yOffset + 6, color_005);
        voxels.fallVoxel(xOffset + 8, yOffset + 7, color_005);
        voxels.fallVoxel(xOffset + 8, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 8, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 8, yOffset + 10, color_007);
        voxels.fallVoxel(xOffset + 9, yOffset + 1, color_000);
        voxels.fallVoxel(xOffset + 9, yOffset + 2, color_002);
        voxels.fallVoxel(xOffset + 9, yOffset + 3, color_001);
        voxels.fallVoxel(xOffset + 9, yOffset + 4, color_004);
        voxels.fallVoxel(xOffset + 9, yOffset + 5, color_004);
        voxels.fallVoxel(xOffset + 9, yOffset + 6, color_005);
        voxels.fallVoxel(xOffset + 9, yOffset + 7, color_006);
        voxels.fallVoxel(xOffset + 9, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 9, yOffset + 9, color_001);
        voxels.fallVoxel(xOffset + 9, yOffset + 10, color_007);
        voxels.fallVoxel(xOffset + 10, yOffset + 1, color_000);
        voxels.fallVoxel(xOffset + 10, yOffset + 2, color_003);
        voxels.fallVoxel(xOffset + 10, yOffset + 3, color_002);
        voxels.fallVoxel(xOffset + 10, yOffset + 4, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 5, color_004);
        voxels.fallVoxel(xOffset + 10, yOffset + 6, color_005);
        voxels.fallVoxel(xOffset + 10, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 10, yOffset + 9, color_007);
        voxels.fallVoxel(xOffset + 11, yOffset + 1, color_000);
        voxels.fallVoxel(xOffset + 11, yOffset + 2, color_003);
        voxels.fallVoxel(xOffset + 11, yOffset + 3, color_003);
        voxels.fallVoxel(xOffset + 11, yOffset + 4, color_002);
        voxels.fallVoxel(xOffset + 11, yOffset + 5, color_001);
        voxels.fallVoxel(xOffset + 11, yOffset + 6, color_006);
        voxels.fallVoxel(xOffset + 11, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 11, yOffset + 8, color_001);
        voxels.fallVoxel(xOffset + 11, yOffset + 9, color_007);
        voxels.fallVoxel(xOffset + 12, yOffset + 2, color_000);
        voxels.fallVoxel(xOffset + 12, yOffset + 3, color_003);
        voxels.fallVoxel(xOffset + 12, yOffset + 4, color_003);
        voxels.fallVoxel(xOffset + 12, yOffset + 5, color_002);
        voxels.fallVoxel(xOffset + 12, yOffset + 6, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 7, color_001);
        voxels.fallVoxel(xOffset + 12, yOffset + 8, color_007);
        voxels.fallVoxel(xOffset + 13, yOffset + 3, color_000);
        voxels.fallVoxel(xOffset + 13, yOffset + 4, color_003);
        voxels.fallVoxel(xOffset + 13, yOffset + 5, color_003);
        voxels.fallVoxel(xOffset + 13, yOffset + 6, color_000);
        voxels.fallVoxel(xOffset + 13, yOffset + 7, color_008);
        voxels.fallVoxel(xOffset + 13, yOffset + 8, color_008);
        voxels.fallVoxel(xOffset + 13, yOffset + 9, color_007);
        voxels.fallVoxel(xOffset + 14, yOffset + 4, color_000);
        voxels.fallVoxel(xOffset + 14, yOffset + 5, color_000);
        voxels.fallVoxel(xOffset + 14, yOffset + 6, color_007);
        voxels.fallVoxel(xOffset + 14, yOffset + 7, color_007);
        voxels.fallVoxel(xOffset + 14, yOffset + 8, color_007);
        voxels.fallVoxel(xOffset + 14, yOffset + 9, color_007);
    }

    private void cutWindow(IVoxelSpace voxels, int xOffset, int y, int zOffset) {
        voxels.removeVoxel(xOffset + 1, y, zOffset +1);
        voxels.removeVoxel(xOffset + 0, y, zOffset +0);
        voxels.removeVoxel(xOffset + 0, y, zOffset +1);
        voxels.removeVoxel(xOffset + 1, y, zOffset +0);
    }

    @Override
    public void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        // fallBed(voxels);
        int xOffset = 4;
        int zOffset = 4;

        int width = 9;
        int depth = 9;
        int height = 5;

        IBlockState dirt = Blocks.DIRT.getDefaultState();
        IBlockState cobble = Blocks.COBBLESTONE.getDefaultState();

        int y = voxels.flattenArea(xOffset, zOffset, width, depth, dirt);
        if(y+height > voxels.getDimension()) {
            y = voxels.getDimension()-height;
        }

        VoxelSpaceTools.hollowBlock(voxels, xOffset, y, zOffset, width, height, depth, cobble);

        cutWindow(voxels, xOffset+2, y+height-1, zOffset+2);
        cutWindow(voxels, xOffset+5, y+height-1, zOffset+5);
        cutWindow(voxels, xOffset+2, y+height-1, zOffset+5);
        cutWindow(voxels, xOffset+5, y+height-1, zOffset+2);

        voxels.removeVoxel(xOffset+4, y+1, zOffset);
        voxels.removeVoxel(xOffset+4, y+2, zOffset);
    }
}
