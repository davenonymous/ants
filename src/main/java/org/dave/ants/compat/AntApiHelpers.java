package org.dave.ants.compat;

import net.minecraft.item.ItemStack;
import org.dave.ants.api.IAntHelpers;
import org.dave.ants.api.IAntStackData;
import org.dave.ants.hills.HillItemStackData;

public class AntApiHelpers implements IAntHelpers {
    @Override
    public IAntStackData getStackInfo(ItemStack stack) {
        return new HillItemStackData(stack);
    }
}
