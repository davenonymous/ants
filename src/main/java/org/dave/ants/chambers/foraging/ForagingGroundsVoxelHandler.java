package org.dave.ants.chambers.foraging;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;

@AntChamberVoxelHandler(antChamberType = ForagingGrounds.class)
public class ForagingGroundsVoxelHandler implements IAntChamberVoxelHandler {
    @Override
    public void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        @SuppressWarnings("deprecation") IBlockState wool = Blocks.WOOL.getStateFromMeta(EnumDyeColor.GREEN.getMetadata());

        int xOffset = 3;
        int yOffset = 4;

        voxels.fallVoxel(xOffset + 2, yOffset + 0, wool);
        voxels.fallVoxel(xOffset + 8, yOffset + 0, wool);
        voxels.fallVoxel(xOffset + 2, yOffset + 1, wool);
        voxels.fallVoxel(xOffset + 9, yOffset + 1, wool);
        voxels.fallVoxel(xOffset + 2, yOffset + 2, wool);
        voxels.fallVoxel(xOffset + 9, yOffset + 2, wool);
        voxels.fallVoxel(xOffset + 10, yOffset + 2, wool);

        voxels.fallVoxel(xOffset + 1, yOffset + 3, wool);
        voxels.fallVoxel(xOffset + 5, yOffset + 3, wool);
        voxels.fallVoxel(xOffset + 6, yOffset + 3, wool);
        voxels.fallVoxel(xOffset + 9, yOffset + 3, wool);
        voxels.fallVoxel(xOffset + 10, yOffset + 3, wool);

        voxels.fallVoxel(xOffset + 1, yOffset + 4, wool);
        voxels.fallVoxel(xOffset + 3, yOffset + 4, wool);
        voxels.fallVoxel(xOffset + 5, yOffset + 4, wool);
        voxels.fallVoxel(xOffset + 6, yOffset + 4, wool);

        voxels.fallVoxel(xOffset + 0, yOffset + 5, wool);
        voxels.fallVoxel(xOffset + 1, yOffset + 5, wool);
        voxels.fallVoxel(xOffset + 3, yOffset + 5, wool);

        voxels.fallVoxel(xOffset + 0, yOffset + 6, wool);
        voxels.fallVoxel(xOffset + 1, yOffset + 6, wool);
        voxels.fallVoxel(xOffset + 5, yOffset + 6, wool);
        voxels.fallVoxel(xOffset + 8, yOffset + 6, wool);
        voxels.fallVoxel(xOffset + 9, yOffset + 6, wool);

        voxels.fallVoxel(xOffset + 4, yOffset + 7, wool);
        voxels.fallVoxel(xOffset + 5, yOffset + 7, wool);
        voxels.fallVoxel(xOffset + 8, yOffset + 7, wool);
        voxels.fallVoxel(xOffset + 9, yOffset + 7, wool);

        voxels.fallVoxel(xOffset + 4, yOffset + 8, wool);
        voxels.fallVoxel(xOffset + 5, yOffset + 8, wool);

    }
}
