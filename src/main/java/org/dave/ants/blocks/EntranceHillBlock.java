package org.dave.ants.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dave.ants.Ants;
import org.dave.ants.chambers.entrance.EntranceChamber;
import org.dave.ants.hills.HillData;
import org.dave.ants.hills.HillItemStackData;
import org.dave.ants.hills.HillRegistry;
import org.dave.ants.tiles.EntranceHillTile;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class EntranceHillBlock extends BaseHillBlock implements ITileEntityProvider {
    public EntranceHillBlock() {
        super("hill_entrance", Material.GRASS, MapColor.BROWN_STAINED_HARDENED_CLAY);

        this.addToCreativeTab();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Ants.MODID, "hill_entrance"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, itemModelResourceLocation);
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new EntranceHillTile();
    }


    // When placing an Entrance block with a hillId:
    // 1. Only ant hills with the same id may be present
    @Override
    public void onBlockPlacedWithHillId(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing side, HillItemStackData hillStackData) {
        HillRegistry hillRegistry = Ants.savedData.hills;
        boolean hasNearbyConflictingHill = Arrays.stream(EnumFacing.HORIZONTALS).anyMatch(enumFacing -> {
            BlockPos testPos = pos.offset(enumFacing);
            HillData hillData = hillRegistry.getHillDataByPosition(world, testPos);
            if(hillData == null) {
                return false;
            }

            return hillData.hillId != hillStackData.getHillId();
        });

        if(hasNearbyConflictingHill) {
            abortPlacement(world, pos, stack, placer);
            return;
        }

        hillRegistry.addNewChamber(world, pos, hillStackData.getHillId(), EntranceChamber.class, hillStackData);
    }

    // When placing an Entrance block without a hillId:
    // 1. No nearby ant Hill maybe present
    @Override
    public void onBlockPlacedWithoutHillId(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing side, HillItemStackData data) {
        HillRegistry hillRegistry = Ants.savedData.hills;
        boolean hasNearbyHill = Arrays.stream(EnumFacing.VALUES).anyMatch(enumFacing -> {
            BlockPos testPos = pos.offset(enumFacing);
            HillData hillData = hillRegistry.getHillDataByPosition(world, testPos);
            return hillData != null;
        });

        if(hasNearbyHill) {
            abortPlacement(world, pos, stack, placer);
            return;
        }

        // Then initialize the hill chamberData for this hill
        HillData hillData = hillRegistry.createNewHill();
        hillRegistry.addNewChamber(world, pos, hillData.hillId, EntranceChamber.class, data);

        getHillBaseTileEntity(world, pos).setHillId(hillData.hillId);
    }


    @Override
    public void addInformationWithoutHillId(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tile.ants.hill.tooltip.must_start_new_colony"));
    }
}
