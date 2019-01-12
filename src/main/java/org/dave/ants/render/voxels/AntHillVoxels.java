package org.dave.ants.render.voxels;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelSpace;
import org.dave.ants.blocks.BaseHillBlock;
import org.dave.ants.api.render.VoxelSpaceTools;
import org.dave.ants.render.properties.UnlistedPropertyHillNeighbors;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

@AntChamberVoxelHandler(antChamberType = Class.class)
public class AntHillVoxels implements IAntChamberVoxelHandler {

    @Override
    public int renderPriority() {
        return -1;
    }

    @Override
    public void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState) {
        UnlistedPropertyHillNeighbors.HillNeighbors neighbors = extendedBlockState.getValue(BaseHillBlock.HILL_NEIGHBORS);


        List<IBlockState> tierList = Collections.EMPTY_LIST;
        Class<? extends IAntChamber> chamberType = extendedBlockState.getValue(BaseHillBlock.CHAMBER_TYPE);
        IAntChamber chamber = Ants.chamberTypes.getChamberInstance(chamberType);
        if(chamber != null && chamber.getTierList().size() > 0) {
            tierList = chamber.getTierList();
        }

        int tier = extendedBlockState.getValue(BaseHillBlock.CHAMBER_TIER);

        IBlockState blockState = Blocks.DIRT.getDefaultState();
        if(tier > 0 && tier < tierList.size()) {
            blockState = tierList.get(tier);
        }

        if(neighbors.up() > 0) {
            // Full block
            VoxelSpaceTools.hollowBlock(voxels, 0, 0, 0, voxels.getDimension(), voxels.getDimension(), voxels.getDimension(), blockState);
            return;
        }

        BufferedImage image = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dDrawer = image.createGraphics();
        g2dDrawer.setBackground(Color.WHITE);
        g2dDrawer.setColor(Color.WHITE);
        g2dDrawer.fillRect(0, 0, 5, 5);

        Color low = new Color(1.0f, 1.0f, 1.0f);
        Color mid = new Color(0.5f, 0.5f, 0.5f);
        Color high = new Color(0.1f, 0.1f, 0.1f);

        for(UnlistedPropertyHillNeighbors.EnumNeighborDirections direction : UnlistedPropertyHillNeighbors.EnumNeighborDirections.values()) {
            if(direction == UnlistedPropertyHillNeighbors.EnumNeighborDirections.UP || direction == UnlistedPropertyHillNeighbors.EnumNeighborDirections.DOWN) {
                continue;
            }

            int height = neighbors.neighborHeights.getOrDefault(direction, 0);
            if(height > 2) {
                height = 2;
            }
            int x = direction.getDirectionVec().getX()+2;
            int y = direction.getDirectionVec().getZ()+2;

            Color color = low;
            if(height == 1) {
                color = mid;
            } else if(height > 1) {
                color = high;
            }

            g2dDrawer.setBackground(color);
            g2dDrawer.setColor(color);
            g2dDrawer.fillRect(x, y, 1, 1);
        }

        // And the center dot
        g2dDrawer.setBackground(mid);
        g2dDrawer.setColor(mid);
        g2dDrawer.fillRect(2, 2, 1, 1);

        g2dDrawer.dispose();


        BufferedImage scaledImage = new BufferedImage(voxels.getDimension() * 5, voxels.getDimension() * 5, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dScaler = scaledImage.createGraphics();
        g2dScaler.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        AffineTransform at = AffineTransform.getScaleInstance(voxels.getDimension(), voxels.getDimension());
        g2dScaler.drawRenderedImage(image, at);
        g2dScaler.dispose();

        for(int x = 0; x < voxels.getDimension(); x++) {
            for (int z = 0; z < voxels.getDimension(); z++) {
                int rgb = scaledImage.getRGB(x+(2*voxels.getDimension()), z+(2*voxels.getDimension()));
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                int rawHeight = level(r+g+b/3, 140, 0.5f, 160);

                int height = voxels.getDimension() - (rawHeight / voxels.getDimension()) - 1;
                for(int y = 0; y < height; y++) {
                    voxels.setVoxel(x, y, z, blockState);
                }
            }
        }
    }

    private int gamma(int color, float gamma) {
        return (int) Math.pow(color, 1.0f / gamma);
    }

    private int levelRange(int gray, int min, int max) {
        return Math.min(Math.max(gray - min, 0) / (max - min), 255);
    }

    private int level(int gray, int min, float gamma, int max) {
        return gamma(levelRange(gray, min, max), gamma);
    }
}
