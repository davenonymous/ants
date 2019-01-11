package org.dave.ants.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.dave.ants.Ants;
import org.dave.ants.init.Blockss;

public class CreativeTabAnts extends CreativeTabs {
    public CreativeTabAnts() {
        super(Ants.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blockss.hillEntrance);
    }

    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
        super.displayAllRelevantItems(p_78018_1_);
    }
}
