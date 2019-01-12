package org.dave.ants.chambers.livingquarters;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.render.VoxelSpaceTools;

@AntChamberVoxelHandler(antChamberType = LivingQuarters.class)
public class LivingQuartersVoxelHandler implements IAntChamberVoxelHandler {
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
