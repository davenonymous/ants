package org.dave.ants.util;

import com.google.common.base.Objects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.dave.ants.api.serialization.Store;
import org.dave.ants.base.BaseNBTSerializable;

import javax.annotation.Nullable;

public class DimPos extends BaseNBTSerializable {
    @Store
    public int dimension;

    @Store
    private BlockPos pos;

    public DimPos() {
    }

    public DimPos(int dimension, BlockPos pos) {
        this.dimension = dimension;
        this.pos = pos;
    }

    public DimPos(World world, BlockPos pos) {
        this.dimension = world.provider.getDimension();
        this.pos = pos;
    }

    @Nullable
    public World getWorld() {
        return DimensionManager.getWorld(this.dimension);
    }

    public BlockPos getBlockPos() {
        return pos;
    }

    public boolean isLoaded() {
        if(getWorld() == null) {
            return false;
        }

        return getWorld().isBlockLoaded(pos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DimPos dimPos = (DimPos) o;
        return dimension == dimPos.dimension &&
                Objects.equal(pos, dimPos.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dimension, pos);
    }

    @Override
    public String toString() {
        return "[" +
                "dimension=" + dimension +
                ", pos=" + pos +
                ']';
    }
}