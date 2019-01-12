package org.dave.ants.chambers.entrance;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.api.render.VoxelSpaceTools;

@AntChamberVoxelHandler(antChamberType = EntranceChamber.class)
public class EntranceChamberVoxelHandler implements IAntChamberVoxelHandler {
    @Override
    public void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        IBlockState material = Blocks.COBBLESTONE.getDefaultState();

        int centerX = voxels.getDimension()/2;
        int centerY = voxels.getDimension()/2;

        VoxelSpaceTools.fallCircle(voxels, centerX, centerY, 5, material);
        VoxelSpaceTools.fallCircle(voxels, centerX, centerY, 4, material);
        VoxelSpaceTools.fallCircle(voxels, centerX, centerY, 3, material);
        VoxelSpaceTools.fallCircle(voxels, centerX, centerY, 2, material);
        VoxelSpaceTools.fallCircle(voxels, centerX, centerY, 1, material);
        for(int y = voxels.getDimension()-1; y > 5; y--) {
            int thisX = centerX;
            int thisY = centerY;

            voxels.removeVoxel(thisX, y, thisY);
        }

    }
}
