package org.dave.ants.api.render;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;

public class VoxelSpaceTools {
    public static void drawCircle(IVoxelSpace voxels, int centerX, int centerY, int centerZ, int radius, BlockLog.EnumAxis axis, IBlockState state) {
        VoxelSpaceTools.drawCircle(voxels, centerX, centerY, centerZ, radius, axis, state, false);
    }

    public static void fallCircle(IVoxelSpace voxels, int centerX, int centerZ, int radius, IBlockState state) {
        for(int y=-radius; y<=radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius) {
                    voxels.fallVoxel(centerX + x, centerZ + y, state);
                }
            }
        }
    }

    public static void drawCircle(IVoxelSpace voxels, int centerX, int centerY, int centerZ, int radius, BlockLog.EnumAxis axis, IBlockState state, boolean filled) {
        if (axis == BlockLog.EnumAxis.NONE) {
            return;
        }

        // Manually, meh. There are other, more efficient algorithms than this.
        // We care more about the simplicity and ability to be able to read this.
        if (axis == BlockLog.EnumAxis.Y) {
            for(int y=-radius; y<=radius; y++) {
                for (int x = -radius; x <= radius; x++) {
                    if (x * x + y * y <= radius * radius) {
                        voxels.setVoxel(centerX + x, centerY, centerZ + y, state);
                    }
                }
            }
        } else if(axis == BlockLog.EnumAxis.Z) {
            for(int y=-radius; y<=radius; y++) {
                for (int x = -radius; x <= radius; x++) {
                    if (x * x + y * y <= radius * radius) {
                        voxels.setVoxel(centerX + x, centerY+y, centerZ, state);
                    }
                }
            }
        } else if(axis == BlockLog.EnumAxis.X) {
            for(int y=-radius; y<=radius; y++) {
                for (int x = -radius; x <= radius; x++) {
                    if (x * x + y * y <= radius * radius) {
                        voxels.setVoxel(centerX, centerY+x, centerZ + y, state);
                    }
                }
            }
        }
    }

    public static void blockFrame(IVoxelSpace voxels, int x, int y, int z, int width, int height, int depth, IBlockState state) {
        block(voxels, x, y, z, width, height, depth, state, true, true);
    }

    public static void hollowBlock(IVoxelSpace voxels, int x, int y, int z, int width, int height, int depth, IBlockState state) {
        block(voxels, x, y, z, width, height, depth, state, true, false);
    }

    public static void block(IVoxelSpace voxels, int x, int y, int z, int width, int height, int depth, IBlockState state, boolean hollow, boolean frameOnly) {
        if(width < 0) {
            x -= width;
            width *= -1;
        }

        if(height < 0) {
            y -= height;
            height *= -1;
        }

        if(depth < 0) {
            z -= depth;
            depth *= -1;
        }

        for(int _x = x; _x < x + width; _x++) {
            for (int _y = y; _y < y + height; _y++) {
                for (int _z = z; _z < z + depth; _z++) {
                    if(hollow && !(_y == y || _y == y+height-1 || _x == x || _x == x+width-1 || _z == z || _z == z+depth-1)) {
                        continue;
                    }

                    if(hollow && frameOnly) {
                        int atMax = 0;
                        int atMin = 0;

                        if(_x == x) {
                            atMin++;
                        }
                        if(_x == x+width-1) {
                            atMax++;
                        }
                        if(_y == y) {
                            atMin++;
                        }
                        if(_y == y+height-1) {
                            atMax++;
                        }
                        if(_z == z) {
                            atMin++;
                        }
                        if(_z == z+depth-1) {
                            atMax++;
                        }

                        if(atMax + atMin < 2) {
                            continue;
                        }
                    }

                    voxels.setVoxel(_x, _y, _z, state);
                }
            }
        }
    }
}
