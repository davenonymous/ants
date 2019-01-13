package org.dave.ants.api.chambers;

import net.minecraft.item.ItemStack;
import org.dave.ants.api.IAntStackData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface IChamberRegistry {
    void registerChamberType(Class<? extends IAntChamber> chamberType);

    ItemStack createItemStackForChamberType(Class<? extends IAntChamber> chamberType, int chamberTier);

    @Nullable
    IAntChamber getChamberInstance(@Nonnull IAntStackData data);

    @Nullable
    IAntChamber getChamberInstance(Class<? extends IAntChamber> type);

    @Nullable
    IAntChamber getChamberInstance(Class<? extends IAntChamber> type, @Nullable IAntStackData data);

    Set<Class<? extends IAntChamber>> getChamberTypes();
}
