package org.dave.ants.hills;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.serialization.Store;
import org.dave.ants.base.BaseWorldSavedData;
import org.dave.ants.chambers.ChamberRegistry;
import org.dave.ants.util.DimPos;
import org.dave.ants.util.Logz;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class HillRegistry extends BaseWorldSavedData {
    @Store
    public int nextHillId = 0;

    @Store
    public Map<Integer, HillData> hillDataMap = new HashMap<>();

    @Store
    public Map<DimPos, Integer> posHillMap = new HashMap<>();


    public HillRegistry(String name) {
        super(name);
    }

    public HillRegistry() {
        this("AntHillRegistry");
    }

    @Override
    public void afterLoad() {
        Logz.info("Loaded Hill Registry. Next hill id=%d", nextHillId);
    }

    public HillData createNewHill() {
        HillData newData = new HillData();
        newData.hillId = getNextHillId();
        newData.markDirty();

        hillDataMap.put(newData.hillId, newData);
        this.markDirty();
        return newData;
    }

    @Nullable
    public IAntChamber addNewChamber(World world, BlockPos pos, int hillId, Class<? extends IAntChamber> type, @Nonnull HillItemStackData data) {
        IAntChamber result = ChamberRegistry.getChamberInstance(type, data);
        if(result == null) {
            return result;
        }

        DimPos dimPos = new DimPos(world, pos);

        HillData hillData = getHillData(hillId);
        result.applyHillModification(hillData, data.getChamberTier());
        hillData.chambers.put(dimPos, result);
        hillData.maxTierLevels.compute(type, (aClass, integer) -> integer == null ? data.getChamberTier() : Math.max(integer, data.getChamberTier()));
        hillData.markDirty();
        posHillMap.put(dimPos, hillData.hillId);
        this.markDirty();

        return result;
    }

    public void removeChamber(World world, BlockPos pos) {
        DimPos dimPos = new DimPos(world, pos);
        HillData hillData = this.getHillDataByPosition(dimPos);
        if(hillData == null) {
            return;
        }

        hillData.chambers.remove(dimPos);
        this.posHillMap.remove(dimPos);
        this.markDirty();

        hillData.updateHillStatistics();
        hillData.markDirty();
    }

    private int getNextHillId() {
        int result = nextHillId;
        this.nextHillId++;
        this.markDirty();
        return result;
    }

    @Nullable
    public HillData getHillData(int id) {
        if(id > nextHillId) {
            return null;
        }

        // Create a new hill on demand
        if(!hillDataMap.containsKey(id)) {
            HillData newData = new HillData();
            newData.hillId = id;
            newData.markDirty();
            hillDataMap.put(id, newData);
            this.markDirty();
        }

        return hillDataMap.get(id);
    }

    public HillData getHillDataByPosition(World world, BlockPos pos) {
        return getHillDataByPosition(new DimPos(world, pos));
    }

    @Nullable
    public HillData getHillDataByPosition(DimPos pos) {
        if(!posHillMap.containsKey(pos)) {
            return null;
        }

        return getHillData(posHillMap.get(pos));
    }

    @Override
    public boolean isDirty() {
        return super.isDirty() || (hillDataMap != null && hillDataMap.values().stream().filter(hillData -> hillData != null).anyMatch(HillData::isDirty));
    }
}
