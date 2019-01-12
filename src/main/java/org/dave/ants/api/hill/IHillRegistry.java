package org.dave.ants.api.hill;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.hills.HillData;

import javax.annotation.Nullable;

public interface IHillRegistry {
    @Nullable
    HillData getHillData(int id);

    HillData getHillDataByPosition(World world, BlockPos pos);
}
