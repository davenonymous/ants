package org.dave.ants.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.base.BaseItemBlock;
import org.dave.ants.chambers.ChamberRegistry;
import org.dave.ants.chambers.entrance.EntranceChamber;

public class ChamberHillItemBlock extends BaseItemBlock {
    public ChamberHillItemBlock(Block block, boolean addToCreativeTab) {
        super(block, addToCreativeTab);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(tab)) {
            for(Class<? extends IAntChamber> chamberClass : ChamberRegistry.getChamberTypes()) {
                if(chamberClass == EntranceChamber.class) {
                    continue;
                }

                items.add(ChamberRegistry.createItemStackForChamberType(chamberClass));
            }
        }
    }


}
