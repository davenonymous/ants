package org.dave.ants.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dave.ants.Ants;
import org.dave.ants.hills.HillData;
import org.dave.ants.hills.HillItemStackData;
import org.dave.ants.hills.HillRegistry;
import org.dave.ants.tiles.ChamberHillTile;

import javax.annotation.Nullable;

public class ChamberHillBlock extends BaseHillBlock implements ITileEntityProvider {

    public ChamberHillBlock() {
        super("hill_chamber", Material.GRASS, MapColor.BROWN_STAINED_HARDENED_CLAY);

        this.addToCreativeTab();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Ants.MODID, "hill_chamber"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, stack -> {
            HillItemStackData hillItemStackData = new HillItemStackData(stack);
            if(!hillItemStackData.hasChamberType()) {
                return itemModelResourceLocation;
            }

            ResourceLocation newResLoc = new ResourceLocation(Ants.MODID, "hill_chamber_" + hillItemStackData.getChamberType().getSimpleName().toLowerCase());
            return new ModelResourceLocation(newResLoc, "inventory");
        });
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ChamberHillTile();
    }

    public static ChamberHillTile getChamberHillTileEntity(IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te == null || !(te instanceof ChamberHillTile)) {
            return null;
        }

        return (ChamberHillTile) te;
    }

    // When placing a normal block with a hill id:
    // 1. no neighbor with a different id may be present
    // 2. at least one neighbor with the same id must be present
    @Override
    public void onBlockPlacedWithHillId(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing side, HillItemStackData hillStackData) {
        HillRegistry hillRegistry = Ants.savedData.hills;
        boolean hasNeighborWithDifferentId = false;
        boolean hasAtLeastOneNeighborWithSameId = false;
        for(EnumFacing direction : EnumFacing.HORIZONTALS) {
            BlockPos testPos = pos.offset(direction);

            HillData hillData = hillRegistry.getHillDataByPosition(world, testPos);
            if(hillData != null) {
                int neighborId = hillData.hillId;
                if(neighborId == hillStackData.getHillId()) {
                    hasAtLeastOneNeighborWithSameId = true;
                } else {
                    hasNeighborWithDifferentId = true;
                    break;
                }
            }
        }

        if(hasNeighborWithDifferentId || !hasAtLeastOneNeighborWithSameId) {
            placer.sendMessage(new TextComponentTranslation("tile.ants.hill.tooltip.must_be_next_to_anthill_with_id", hillStackData.getHillId()));
            abortPlacement(world, pos, stack, placer);
            return;
        }

        hillRegistry.addNewChamber(world, pos, hillStackData.getHillId(), hillStackData.getChamberType(), hillStackData);

        ChamberHillTile hillTile = getChamberHillTileEntity(world, pos);
        hillTile.setChamberType(hillStackData.getChamberType());
        hillTile.setChamberTier(hillStackData.getChamberTier());
        world.markBlockRangeForRenderUpdate(pos, pos);
    }

    // When placing a normal block without a hill id:
    // 1. The block placed against must have an ID or one of the neighbors must have an id
    // 2. No other block with a different ID may exist
    @Override
    public void onBlockPlacedWithoutHillId(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing side, HillItemStackData hillStackData) {
        // First: Determine the ID this hill should get
        HillRegistry hillRegistry = Ants.savedData.hills;

        int newHillId = -1;
        BlockPos placedAgainstPos = pos.offset(side.getOpposite());
        HillData placedAgainstHillData = hillRegistry.getHillDataByPosition(world, placedAgainstPos);
        if(placedAgainstHillData != null) {
            newHillId = placedAgainstHillData.hillId;
        } else {
            for(EnumFacing direction : EnumFacing.HORIZONTALS) {
                BlockPos testPos = pos.offset(direction);

                HillData neighborHillData = hillRegistry.getHillDataByPosition(world, testPos);
                if (neighborHillData != null) {
                    newHillId = neighborHillData.hillId;
                    break;
                }
            }
        }

        // Abort if there is no hill nearby
        if(newHillId == -1) {
            placer.sendMessage(new TextComponentTranslation("tile.ants.hill.tooltip.must_be_next_to_anthill"));
            abortPlacement(world, pos, stack, placer);
            return;
        }

        // Check if there is a conflicting hill nearby
        for(EnumFacing direction : EnumFacing.HORIZONTALS) {
            BlockPos testPos = pos.offset(direction);

            HillData neighborHillData = hillRegistry.getHillDataByPosition(world, testPos);
            if (neighborHillData != null) {
                int neighborId = neighborHillData.hillId;

                if(neighborId != newHillId) {
                    placer.sendMessage(new TextComponentTranslation("tile.ants.hill.tooltip.can_not_touch"));
                    abortPlacement(world, pos, stack, placer);
                    return;
                }
            }
        }

        hillRegistry.addNewChamber(world, pos, newHillId, hillStackData.getChamberType(), hillStackData);
        ChamberHillTile hillTile = getChamberHillTileEntity(world, pos);
        hillTile.setChamberType(hillStackData.getChamberType());
        hillTile.setChamberTier(hillStackData.getChamberTier());
        hillTile.setHillId(newHillId);
        hillTile.markDirty();
    }
}
