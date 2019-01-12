package org.dave.ants.api.render;

import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;

public interface IVoxelHandlerRegistry {
    void registerVoxelHandler(Class<? extends IAntChamber> chamber, IAntChamberVoxelHandler handler);

    void registerGlobalVoxelHandler(IAntChamberVoxelHandler handler);
}
