package org.dave.ants.api.render;

import net.minecraft.block.state.IBlockState;

public class Voxel {
    public int x, y, z;
    public IBlockState state;

    public Voxel(int x, int y, int z, IBlockState state) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = state;
    }
}
