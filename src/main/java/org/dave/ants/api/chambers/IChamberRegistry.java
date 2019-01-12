package org.dave.ants.api.chambers;

import net.minecraft.item.ItemStack;
import org.dave.ants.hills.HillItemStackData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface IChamberRegistry {
    void registerChamberType(Class<? extends IAntChamber> chamberType);

    ItemStack createItemStackForChamberType(Class<? extends IAntChamber> chamberType);

    @Nullable
    IAntChamber getChamberInstance(@Nonnull HillItemStackData data);

    @Nullable
    IAntChamber getChamberInstance(Class<? extends IAntChamber> type);

    @Nullable
    IAntChamber getChamberInstance(Class<? extends IAntChamber> type, @Nullable HillItemStackData data);

    Set<Class<? extends IAntChamber>> getChamberTypes();
}
