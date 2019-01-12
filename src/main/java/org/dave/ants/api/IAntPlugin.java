package org.dave.ants.api;

import org.dave.ants.api.actions.IActionRegistry;
import org.dave.ants.api.calculation.ICalculationRegistry;
import org.dave.ants.api.chambers.IChamberRegistry;
import org.dave.ants.api.hill.IHillRegistry;
import org.dave.ants.api.properties.IHillPropertyRegistry;
import org.dave.ants.api.render.IVoxelHandlerRegistry;

/**
 * This is the main starting point of your Ants plugin!
 * Implement this interface and use the provided registries as necessary.
 * You also have to annotate the class implementing this with @{@link AntPlugin}
 */
public interface IAntPlugin {
    default void registryReady(IChamberRegistry chamberRegistry) {
    }

    default void registryReady(ICalculationRegistry calculationRegistry) {
    }

    default void registryReady(IHillPropertyRegistry hillPropertyRegistry) {
    }

    default void registryReady(IVoxelHandlerRegistry voxelHandlerRegistry) {
    }

    default void registryReady(IActionRegistry actionRegistry) {
    }

    /**
     * {@link IAntHelpers} provides a few utility methods that might be
     * useful for your plugin.
     *
     * @param helpers
     */
    default void helpersReady(IAntHelpers helpers) {
    }

    /**
     * This is called when the global hill registry is loaded. This can happen
     * multiple times, e.g. everytime the overworld dimension is being loaded.
     *
     * @param hills A registry giving you access to all ant hills.
     */
    default void worldHillRegistryReady(IHillRegistry hills) {
    }

}
