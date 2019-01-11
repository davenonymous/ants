package org.dave.ants.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.util.Logz;

import java.util.*;

public class VoxelSpace implements IVoxelSpace {
    private static final IBlockState AIR = Blocks.AIR.getDefaultState();

    public int dimension = 16;
    public Map<BlockPos, IBlockState> matrix = new HashMap<>();
    public Map<IBlockState, Integer> stateCounter = new HashMap<>();
    public Map<BlockPos, Set<EnumFacing>> faceMap = new HashMap<>();

    @Override
    public IBlockState getVoxel(int x, int y, int z) {
        if(!checkBounds(x) || !checkBounds(y) || !checkBounds(z)) {
            Logz.debug("Unable to get voxel at position %d,%d,%d. Out of bounds!", x, y, z);
            return AIR;
        }

        BlockPos pos = new BlockPos(x, y, z);
        if(!matrix.containsKey(pos)) {
            return AIR;
        }

        return matrix.get(pos);
    }

    private boolean hasVoxel(BlockPos pos) {
        IBlockState state = matrix.get(pos);
        return (state != null && state != AIR);
    }

    @Override
    public void setVoxel(int x, int y, int z, IBlockState state) {
        if(!checkBounds(x) || !checkBounds(y) || !checkBounds(z)) {
            Logz.debug("Unable to set voxel to '%s' at position %d,%d,%d. Out of bounds!", state, x, y, z);
            return;
        }

        decreaseStateCounter(getVoxel(x, y, z));
        matrix.put(new BlockPos(x, y, z), state);
        increaseStateCounter(state);

        BlockPos thisPos = new BlockPos(x, y, z);
        if(state == AIR && faceMap.get(thisPos) != null) {
            // Remove all our own faces
            faceMap.get(thisPos).clear();
        }

        for(EnumFacing dir : EnumFacing.values()) {
            BlockPos checkPos = thisPos.offset(dir);
            if(state == AIR) {
                // Remove a block -> add touching faces
                if(!hasVoxel(checkPos)) {
                    // Neighbor also has no voxel -> nothing to do
                    continue;
                }

                // Neighbor has a voxel and now needs a new face
                if(!faceMap.containsKey(checkPos)) {
                    faceMap.put(checkPos, new HashSet<>());
                }

                faceMap.get(checkPos).add(dir.getOpposite());
            } else {
                // Adding a block -> remove touching faces
                if(!faceMap.containsKey(checkPos) || faceMap.get(checkPos).size() == 0) {
                    if(!faceMap.containsKey(thisPos)) {
                        faceMap.put(thisPos, new HashSet<>());
                    }

                    faceMap.get(thisPos).add(dir);
                } else {
                    faceMap.get(checkPos).remove(dir.getOpposite());
                    if(faceMap.get(checkPos).size() == 0) {
                        faceMap.remove(checkPos);
                    }
                }

            }
        }
    }

    @Override
    public void fallVoxel(int x, int z, IBlockState state) {
        int newHeight = getHeightAt(x, z)+1;
        if(!checkBounds(newHeight)) {
            return;
        }

        setVoxel(x, newHeight, z, state);
    }

    @Override
    public void removeVoxel(int x, int y, int z) {
        setVoxel(x, y, z, AIR);
    }

    @Override
    public int getHeightAt(int x, int z) {
        for(int y = dimension; y >= 0; y--) {
            if(getVoxel(x, y, z) != AIR) {
                return y;
            }
        }

        return -1;
    }

    @Override
    public int getAverageHeight(int x, int z, int w, int h) {
        int sum = 0;
        for(int _x = x; _x < x+w; _x++) {
            for(int _z = z; _z < z+h; _z++) {
                for(int y = dimension; y >= 0; y--) {
                    if(getVoxel(_x, y, _z) != AIR) {
                        sum += y;
                        break;
                    }
                }
            }
        }

        return (int) Math.round((double)sum / (double)(w * h));
    }

    @Override
    public int flattenArea(int x, int z, int w, int d, IBlockState floor) {
        int averageHeight = getAverageHeight(x, z, w, d);
        for(int _x = x; _x < x+w; _x++) {
            for(int _z = z; _z < z+d; _z++) {
                for(int y = dimension; y > averageHeight; y--) {
                    removeVoxel(_x, y, _z);
                }

                if(floor != AIR) {
                    for(int y = averageHeight; y >= 0; y--) {
                        if(getVoxel(_x, y, _z) != AIR) {
                            break;
                        }

                        setVoxel(_x, y, _z, floor);
                    }
                }
            }
        }

        return averageHeight;
    }

    @Override
    public int getDimension() {
        return this.dimension;
    }

    @Override
    public TextureAtlasSprite getMostUsedStateTexture() {
        IBlockState mostCommonState = AIR;
        if(stateCounter.size() > 0) {
            mostCommonState = stateCounter.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
        }

        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(mostCommonState);
    }

    public Map<BlockPos, IBlockState> getVoxels() {
        return matrix;
    }

    private boolean checkBounds(int value) {
        return value >= 0 && value < dimension;
    }

    private void increaseStateCounter(IBlockState state) {
        if(state == AIR) {
            return;
        }

        if(stateCounter.containsKey(state)) {
            stateCounter.put(state, stateCounter.get(state) + 1);
        } else {
            stateCounter.put(state, 1);
        }
    }

    private void decreaseStateCounter(IBlockState state) {
        if(state == AIR) {
            return;
        }

        if(!stateCounter.containsKey(state)) {
            return;
        }

        int newValue = stateCounter.get(state) - 1;
        if(newValue < 1) {
            stateCounter.remove(state);
        } else {
            stateCounter.put(state, newValue);
        }
    }
}
