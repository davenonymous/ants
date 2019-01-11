package org.dave.ants.api.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 * Do not implement this yourself. You get this passed in your IAntChamberVoxelHandler.
 */
public interface IVoxelSpace {
    IBlockState getVoxel(int x, int y, int z);

    void setVoxel(int x, int y, int z, IBlockState state);
    void fallVoxel(int x, int z, IBlockState state);

    void removeVoxel(int x, int y, int z);

    /**
     * Automatically flattens the given area to the average height for the same area
     *
     * @param x xOffset
     * @param z yOffset
     * @param w Width of the area to flatten
     * @param d Depth or the area to flatten
     * @param floor The block that should be used to even out the floor. Use AIR to leave empty.
     *
     * @return The height the area has been flattend to
     */
    int flattenArea(int x, int z, int w, int d, IBlockState floor);

    int getHeightAt(int x, int z);
    int getAverageHeight(int x, int z, int w, int h);
    int getDimension();

    TextureAtlasSprite getMostUsedStateTexture();
}
