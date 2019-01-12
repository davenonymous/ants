package org.dave.ants.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.blocks.BaseHillBlock;
import org.dave.ants.chambers.VoxelHandlerRegistry;
import org.dave.ants.render.properties.UnlistedPropertyHillNeighbors;
import org.dave.ants.util.Logz;
import org.dave.ants.util.Tuple3;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class ChamberHillBakedModel implements IBakedModel {
    public static final ModelResourceLocation BAKED_MODEL = new ModelResourceLocation(Ants.MODID + ":bakedchamberhill");

    private TextureAtlasSprite mostCommonTexture;
    private VertexFormat format;

    public ChamberHillBakedModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        this.format = format;
        this.mostCommonTexture = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/dirt"));
    }

    private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, double u, double v, TextureAtlasSprite sprite) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float)x, (float)y, (float)z, 1.0f);
                    break;
                case COLOR:
                    builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
                    break;
                case UV:
                    if (format.getElement(e).getIndex() == 0) {
                        u = sprite.getInterpolatedU(u);
                        v = sprite.getInterpolatedV(v);
                        builder.put(e, (float)u, (float)v, 0f, 1f);
                        break;
                    }
                case NORMAL:
                    builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

    private BakedQuad createQuad(EnumFacing facing, Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite, float scale) {
        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        double u = 0.0f;
        double v = 0.0f;

        // Get the proper texture coordinates for the given quad
        if(facing == EnumFacing.DOWN) {
            u = v1.x * 16.0f;
            v = v1.z * 16.0f;
        } else if(facing == EnumFacing.UP) {
            u = v4.x * 16.0f;
            v = v4.z * 16.0f;
        } else if(facing == EnumFacing.EAST) {
            u = v1.z * 16.0f;
            v = v1.y * 16.0f;
        } else if(facing == EnumFacing.WEST) {
            u = v4.z * 16.0f;
            v = v4.y * 16.0f;
        } else if(facing == EnumFacing.SOUTH) {
            u = v1.x * 16.0f;
            v = v1.y * 16.0f;
        } else if(facing == EnumFacing.NORTH) {
            u = v4.x * 16.0f;
            v = v4.y * 16.0f;
        }

        double extraUV = 16.0d / scale;

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setTexture(sprite);
        builder.setApplyDiffuseLighting(true);

        if(facing.getAxis() == EnumFacing.Axis.Z) {
            putVertex(builder, normal, v1.x, v1.y, v1.z, u+0,       v+extraUV, sprite);
            putVertex(builder, normal, v2.x, v2.y, v2.z, u+extraUV, v+extraUV, sprite);
            putVertex(builder, normal, v3.x, v3.y, v3.z, u+extraUV, v+0, sprite);
            putVertex(builder, normal, v4.x, v4.y, v4.z, u+0,       v+0, sprite);
        } else if(facing.getAxis() == EnumFacing.Axis.X) {
            putVertex(builder, normal, v1.x, v1.y, v1.z, u+0,       v+0, sprite);
            putVertex(builder, normal, v2.x, v2.y, v2.z, u+0,       v+extraUV, sprite);
            putVertex(builder, normal, v3.x, v3.y, v3.z, u+extraUV, v+extraUV, sprite);
            putVertex(builder, normal, v4.x, v4.y, v4.z, u+extraUV, v+0, sprite);
        } else {
            putVertex(builder, normal, v1.x, v1.y, v1.z, u+extraUV, v+0, sprite);
            putVertex(builder, normal, v2.x, v2.y, v2.z, u+0,       v+0, sprite);
            putVertex(builder, normal, v3.x, v3.y, v3.z, u+0,       v+extraUV, sprite);
            putVertex(builder, normal, v4.x, v4.y, v4.z, u+extraUV, v+extraUV, sprite);
        }

        return builder.build();
    }

    private HashMap<IBlockState, TextureAtlasSprite> spriteCache;
    private TextureAtlasSprite getTextureForState(IBlockState state) {
        if(spriteCache == null) {
            spriteCache = new HashMap<>();
        }

        if(!spriteCache.containsKey(state)) {
            spriteCache.put(state, Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
        }

        return spriteCache.get(state);
    }

    private void bakeFace(List<BakedQuad> quads, VoxelSpace voxelSpace, BlockPos pos, Set<EnumFacing> facings, IBlockState state) {
        double stepSize = 1.0d / voxelSpace.dimension;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        TextureAtlasSprite sprite = getTextureForState(state);

        double xStart = x * stepSize;
        double yStart = y * stepSize;
        double zStart = z * stepSize;

        double xLeft = xStart;
        double xRight = xStart + stepSize;
        double yBottom = yStart;
        double yTop = yStart + stepSize;
        double zFront = zStart;
        double zBack = zStart + stepSize;

        float scale = voxelSpace.dimension;
        for(EnumFacing facing : facings) {
            if(facing == EnumFacing.UP) {
                quads.add(createQuad(EnumFacing.UP, new Vec3d(xLeft, yTop, zBack), new Vec3d(xRight, yTop, zBack), new Vec3d(xRight, yTop, zFront), new Vec3d(xLeft, yTop, zFront), sprite, scale));
            } else if(facing == EnumFacing.DOWN) {
                quads.add(createQuad(EnumFacing.DOWN, new Vec3d(xLeft, yBottom, zFront), new Vec3d(xRight, yBottom, zFront), new Vec3d(xRight, yBottom, zBack), new Vec3d(xLeft, yBottom, zBack), sprite, scale));
            } else if(facing == EnumFacing.EAST) {
                quads.add(createQuad(EnumFacing.EAST, new Vec3d(xRight, yBottom, zFront), new Vec3d(xRight, yTop, zFront), new Vec3d(xRight, yTop, zBack), new Vec3d(xRight, yBottom, zBack), sprite, scale));
            } else if(facing == EnumFacing.WEST) {
                quads.add(createQuad(EnumFacing.WEST, new Vec3d(xLeft, yBottom, zBack), new Vec3d(xLeft, yTop, zBack), new Vec3d(xLeft, yTop, zFront), new Vec3d(xLeft, yBottom, zFront), sprite, scale));
            } else if(facing == EnumFacing.NORTH) {
                quads.add(createQuad(EnumFacing.NORTH, new Vec3d(xLeft, yTop, zFront), new Vec3d(xRight, yTop, zFront), new Vec3d(xRight, yBottom, zFront), new Vec3d(xLeft, yBottom, zFront), sprite, scale));
            } else if(facing == EnumFacing.SOUTH) {
                quads.add(createQuad(EnumFacing.SOUTH, new Vec3d(xLeft, yBottom, zBack), new Vec3d(xRight, yBottom, zBack), new Vec3d(xRight, yTop, zBack), new Vec3d(xLeft, yTop, zBack), sprite, scale));
            }
        }
    }
/*
    private void bakeVoxel(List<BakedQuad> quads, BlockPos pos, IBlockState state) {
        double stepSize = 1.0d / 16.0d;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();


        TextureAtlasSprite sprite = getTextureForState(state);

        double xStart = x * stepSize;
        double yStart = y * stepSize;
        double zStart = z * stepSize;

        double xLeft = xStart;
        double xRight = xStart + stepSize;
        double yBottom = yStart;
        double yTop = yStart + stepSize;
        double zFront = zStart;
        double zBack = zStart + stepSize;

        // up
        quads.add(createQuad(EnumFacing.UP, new Vec3d(xLeft, yTop, zBack), new Vec3d(xRight, yTop, zBack), new Vec3d(xRight, yTop, zFront), new Vec3d(xLeft, yTop, zFront), sprite));

        // down
        quads.add(createQuad(EnumFacing.DOWN, new Vec3d(xLeft, yBottom, zFront), new Vec3d(xRight, yBottom, zFront), new Vec3d(xRight, yBottom, zBack), new Vec3d(xLeft, yBottom, zBack), sprite));

        // east
        quads.add(createQuad(EnumFacing.EAST, new Vec3d(xRight, yBottom, zFront), new Vec3d(xRight, yTop, zFront), new Vec3d(xRight, yTop, zBack), new Vec3d(xRight, yBottom, zBack), sprite));

        // west
        quads.add(createQuad(EnumFacing.WEST, new Vec3d(xLeft, yBottom, zBack), new Vec3d(xLeft, yTop, zBack), new Vec3d(xLeft, yTop, zFront), new Vec3d(xLeft, yBottom, zFront), sprite));

        // north
        quads.add(createQuad(EnumFacing.NORTH, new Vec3d(xLeft, yTop, zFront), new Vec3d(xRight, yTop, zFront), new Vec3d(xRight, yBottom, zFront), new Vec3d(xLeft, yBottom, zFront), sprite));

        // south
        quads.add(createQuad(EnumFacing.SOUTH, new Vec3d(xLeft, yBottom, zBack), new Vec3d(xRight, yBottom, zBack), new Vec3d(xRight, yTop, zBack), new Vec3d(xLeft, yTop, zBack), sprite));
    }
*/
    private static Map<Tuple3<Class<? extends IAntChamber>, Integer, UnlistedPropertyHillNeighbors.HillNeighbors>, List<BakedQuad>> tupleCachedQuads;

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side != null) {
            return Collections.emptyList();
        }

        IExtendedBlockState extendedBlockState = (IExtendedBlockState)state;
        Class<? extends IAntChamber> chamberType = extendedBlockState.getValue(BaseHillBlock.CHAMBER_TYPE);
        //Logz.info("Custom baking for chamber type: %s", chamberType);

        if(chamberType == null) {
            return Collections.emptyList();
        }

        if(tupleCachedQuads == null) {
            tupleCachedQuads = new HashMap<>();
        }

        int chamberTier = extendedBlockState.getValue(BaseHillBlock.CHAMBER_TIER);

        UnlistedPropertyHillNeighbors.HillNeighbors neighbors = extendedBlockState.getValue(BaseHillBlock.HILL_NEIGHBORS);
        Tuple3<Class<? extends IAntChamber>, Integer, UnlistedPropertyHillNeighbors.HillNeighbors> thisTuple3 = new Tuple3<>(chamberType, chamberTier, neighbors);

        if(!tupleCachedQuads.containsKey(thisTuple3)) {
            List<BakedQuad> quads = new ArrayList<>();
            VoxelSpace voxels = Ants.voxelHandlers.getVoxelsForChamberType(chamberType, extendedBlockState);

            for(Map.Entry<BlockPos, Set<EnumFacing>> entry : voxels.faceMap.entrySet()) {
                IBlockState voxelState = voxels.getVoxels().get(entry.getKey());
                bakeFace(quads, voxels, entry.getKey(), entry.getValue(), voxelState);
            }

            mostCommonTexture = voxels.getMostUsedStateTexture();
            tupleCachedQuads.put(thisTuple3, quads);
        }

        return tupleCachedQuads.get(thisTuple3);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return mostCommonTexture;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }
}
