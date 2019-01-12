package org.dave.ants.chambers;

import net.minecraftforge.common.property.IExtendedBlockState;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.render.IVoxelHandlerRegistry;
import org.dave.ants.render.VoxelSpace;
import org.dave.ants.util.AnnotatedInstanceUtil;
import org.dave.ants.util.Logz;
import org.objectweb.asm.Type;

import java.util.*;
import java.util.stream.Collectors;

public class VoxelHandlerRegistry implements IVoxelHandlerRegistry {
    private Map<Class<? extends IAntChamber>, List<IAntChamberVoxelHandler>> voxelHandlers;

    @Override
    public void registerVoxelHandler(Class<? extends IAntChamber> chamber, IAntChamberVoxelHandler handler) {
        if(!voxelHandlers.containsKey(chamber)) {
            voxelHandlers.put(chamber, new ArrayList<>());
        }

        Logz.info("Registering voxelhandler '%s' for chamber '%s'", handler.getClass().getName(), chamber.getName());
        voxelHandlers.get(chamber).add(handler);
    }

    @Override
    public void registerGlobalVoxelHandler(IAntChamberVoxelHandler handler) {
        for (Class<? extends IAntChamber> chamber : Ants.chamberTypes.getChamberTypes()) {
            registerVoxelHandler(chamber, handler);
        }
    }

    public void loadVoxelHandlers() {
        voxelHandlers = new HashMap<>();

        for(Map.Entry<IAntChamberVoxelHandler, Map<String, Object>> entry : AnnotatedInstanceUtil.getVoxelHandlers().entrySet()) {
            Type type = (Type)entry.getValue().get("antChamberType");

            try {
                Class antChamberClass = Class.forName(type.getClassName());
                if(antChamberClass == Class.class) {
                    registerGlobalVoxelHandler(entry.getKey());
                } else {
                    registerVoxelHandler(antChamberClass, entry.getKey());
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public VoxelSpace getVoxelsForChamberType(Class<? extends IAntChamber> chamber, IExtendedBlockState extendedBlockState) {
        VoxelSpace voxelSpace = new VoxelSpace();
        voxelSpace.dimension = 16;

        if(!voxelHandlers.containsKey(chamber)) {
            return voxelSpace;
        }

        for(IAntChamberVoxelHandler handler : voxelHandlers.get(chamber).stream().sorted(Comparator.comparingInt(IAntChamberVoxelHandler::renderPriority)).collect(Collectors.toList())) {
            handler.addVoxels(voxelSpace, extendedBlockState);
        }

        return voxelSpace;
    }
}
