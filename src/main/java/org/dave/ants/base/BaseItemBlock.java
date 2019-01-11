package org.dave.ants.base;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.hills.HillItemStackData;

public class BaseItemBlock extends ItemBlock {
    public BaseItemBlock(Block block, boolean addToCreativeTab) {
        super(block);
        this.setRegistryName(block.getRegistryName());
        if(addToCreativeTab) {
            this.setCreativeTab(Ants.CREATIVE_TAB);
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        HillItemStackData data = new HillItemStackData(stack);
        if(data == null) {
            return super.getTranslationKey(stack);
        }

        Class<? extends IAntChamber> type = data.getChamberType();
        if(type == null) {
            return super.getTranslationKey(stack);
        }
        return super.getTranslationKey(stack) + "." + type.getSimpleName().toLowerCase();
    }

    // This is the original code from ItemBlock, but changed to also pass along the side that has been hit
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.block && this.block instanceof BaseBlock) {
            BaseBlock baseBlock = (BaseBlock)this.block;

            setTileEntityNBT(world, player, pos, stack);
            baseBlock.onBlockPlacedBySided(world, pos, state, player, stack, side);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return true;

    }
}
