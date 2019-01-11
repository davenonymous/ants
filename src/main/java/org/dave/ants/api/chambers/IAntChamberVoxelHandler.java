package org.dave.ants.api.chambers;

import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.api.render.IVoxelSpace;

public interface IAntChamberVoxelHandler {
    /**
     * This is called when the model for this block (and its neighbors) is being baked.
     * Use the IVoxelSpace given to add/remove voxels.
     *
     * @param voxels
     * @param extendedBlockState
     */
    void addVoxels(IVoxelSpace voxels, IExtendedBlockState extendedBlockState);

    /**
     * Return the priority with which this ant chamber should be renderer.
     * Lower is higher. If you go below 0 you will place voxels before the default
     * ant hill generation has happened and your voxels will likely be overwritten.
     *
     * @return priority with which to render.
     */
    default int renderPriority() {
        return 0;
    }
}
