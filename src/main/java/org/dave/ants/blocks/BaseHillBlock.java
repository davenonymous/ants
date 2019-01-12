package org.dave.ants.blocks;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.properties.stored.TotalAnts;
import org.dave.ants.base.BaseBlock;
import org.dave.ants.compat.TheOneProbe.ITopInfoProvider;
import org.dave.ants.hills.HillData;
import org.dave.ants.hills.HillItemStackData;
import org.dave.ants.hills.HillRegistry;
import org.dave.ants.network.AntsNetworkHandler;
import org.dave.ants.network.ChamberDataMessage;
import org.dave.ants.proxy.GuiProxy;
import org.dave.ants.render.ChamberHillBakedModel;
import org.dave.ants.render.properties.UnlistedPropertyChamberTier;
import org.dave.ants.render.properties.UnlistedPropertyChamberType;
import org.dave.ants.render.properties.UnlistedPropertyHillNeighbors;
import org.dave.ants.tiles.BaseHillTile;
import org.dave.ants.util.DimPos;
import org.dave.ants.util.SmartNumberFormatter;

import javax.annotation.Nullable;
import java.util.List;

public class BaseHillBlock extends BaseBlock implements ITopInfoProvider {
    public static final UnlistedPropertyChamberType CHAMBER_TYPE = new UnlistedPropertyChamberType();
    public static final UnlistedPropertyHillNeighbors HILL_NEIGHBORS = new UnlistedPropertyHillNeighbors();
    public static final UnlistedPropertyChamberTier CHAMBER_TIER = new UnlistedPropertyChamberTier();

    public BaseHillBlock(String name, Material blockMaterial, MapColor blockMapColor) {
        super(name, blockMaterial, blockMapColor);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        IProperty[] listedProperties = new IProperty[0];
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { CHAMBER_TYPE, HILL_NEIGHBORS, CHAMBER_TIER };
        return new ExtendedBlockState(this, listedProperties, unlistedProperties);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return ChamberHillBakedModel.BAKED_MODEL;
            }
        };

        ModelLoader.setCustomStateMapper(this, ignoreState);
    }


    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        // Add which kind of chamber and tier this block is
        BaseHillTile hillTile = getHillBaseTileEntity(world, pos);
        if(hillTile != null) {
            Class<? extends IAntChamber> type = hillTile.getChamberType();
            extendedBlockState = extendedBlockState.withProperty(CHAMBER_TYPE, type);
            extendedBlockState = extendedBlockState.withProperty(CHAMBER_TIER, hillTile.getChamberTier());
        }

        // Gather chamberData about the neighboring hills
        UnlistedPropertyHillNeighbors.HillNeighbors hillNeighbors = new UnlistedPropertyHillNeighbors.HillNeighbors();
        for(UnlistedPropertyHillNeighbors.EnumNeighborDirections direction : UnlistedPropertyHillNeighbors.EnumNeighborDirections.values()) {
            BaseHillTile hillTileNeighbor = BaseHillBlock.getHillBaseTileEntity(world, direction.offset(pos));
            if(hillTileNeighbor == null) {
                continue;
            }

            hillNeighbors.setNeighborHeight(direction, 1);

            // Check above!
            BaseHillTile hillTileNeighborAbove = BaseHillBlock.getHillBaseTileEntity(world, direction.offset(pos).up());
            if(hillTileNeighborAbove == null) {
                continue;
            }

            hillNeighbors.setNeighborHeight(direction, 2);
        }
        extendedBlockState = extendedBlockState.withProperty(HILL_NEIGHBORS, hillNeighbors);

        return extendedBlockState;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }


    public void abortPlacement(World world, BlockPos pos, ItemStack stack, EntityLivingBase placer) {
        if(placer instanceof EntityPlayer && !((EntityPlayer) placer).isCreative()) {
            EntityItem entityItem = new EntityItem(world, pos.getX()+0.5f, pos.getY()+0.2f, pos.getZ()+0.5f, stack);
            entityItem.lifespan = 600;
            entityItem.setPickupDelay(5);

            entityItem.motionX = 0.0f;
            entityItem.motionY = 0.10f;
            entityItem.motionZ = 0.0f;

            world.spawnEntity(entityItem);

        }

        world.setBlockToAir(pos);
    }

    public static BaseHillTile getHillBaseTileEntity(IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if(te == null || !(te instanceof BaseHillTile)) {
            return null;
        }

        return (BaseHillTile)te;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);

        if(world.isRemote) {
            return;
        }

        HillRegistry hillRegistry = Ants.savedData.hills;
        hillRegistry.removeChamber(world, pos);
    }

    @Override
    public void onBlockPlacedBySided(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing side) {
        super.onBlockPlacedBySided(world, pos, state, placer, stack, side);

        if(world.isRemote) {
            return;
        }

        HillItemStackData data = new HillItemStackData(stack);
        if(data.hasHillId()) {
            // This is a block that has either been picked up or given by an ant hill
            this.onBlockPlacedWithHillId(world, pos, state, placer, stack, side, data);
        } else {
            // This is either a new Core or an addon placed via creative mode.
            this.onBlockPlacedWithoutHillId(world, pos, state, placer, stack, side, data);
        }
    }

    public void onBlockPlacedWithHillId(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing side, HillItemStackData hillStackData) {
    }

    public void onBlockPlacedWithoutHillId(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, EnumFacing side, HillItemStackData hillStackData) {
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(player.isSneaking()) {
            return false;
        }

        if(world.isRemote || !(player instanceof EntityPlayerMP)) {
            return true;
        }

        BaseHillTile hillTile = BaseHillBlock.getHillBaseTileEntity(world, pos);
        if(hillTile == null) {
            return false;
        }

        HillData hillData = Ants.savedData.hills.getHillData(hillTile.getHillId());
        if(hillData == null) {
            return false;
        }

        IAntChamber liveChamberData = hillData.chambers.get(new DimPos(world, pos));
        if(liveChamberData == null) {
            return false;
        }

        player.openGui(Ants.instance, GuiProxy.GuiIDS.ANT_HILL.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
        AntsNetworkHandler.instance.sendTo(new ChamberDataMessage(hillTile.getChamberType(), liveChamberData.serializeNBT(), hillData.getPropertiesTag(), hillData.getMaxTierLevelsTag(), new DimPos(world, pos)), (EntityPlayerMP) player);
        return true;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        BaseHillTile hillTe = getHillBaseTileEntity(world, pos);
        if(hillTe == null) {
            return ItemStack.EMPTY;
        }

        return hillTe.createItem();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        HillItemStackData data = new HillItemStackData(stack);
        if(data.hasHillId()) {
            tooltip.add(I18n.format("tile.ants.hill.tooltip.must_be_next_to_anthill_with_id", data.getHillId()));
        } else {
            addInformationWithoutHillId(stack, worldIn, tooltip, flagIn);
        }
    }

    public void addInformationWithoutHillId(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tile.ants.hill.tooltip.must_be_next_to_anthill"));
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        HillData hillData = Ants.savedData.hills.getHillDataByPosition(world, data.getPos());
        if(hillData == null) {
            return;
        }

        probeInfo.horizontal().text("{*tile.ants.hill.overlay.id*} " + hillData.hillId);
        probeInfo.horizontal().text("{*tile.ants.hill.overlay.ants*} " + SmartNumberFormatter.formatNumber(hillData.getPropertyValue(TotalAnts.class)));
        probeInfo.horizontal().text("{*tile.ants.hill.overlay.chambers*} " + hillData.chambers.size());
    }
}
