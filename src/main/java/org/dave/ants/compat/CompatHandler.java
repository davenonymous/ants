package org.dave.ants.compat;

import net.minecraftforge.fml.common.Loader;
import org.dave.ants.compat.TheOneProbe.TopCompatibility;
import org.dave.ants.util.Logz;

public class CompatHandler {
    public static void registerCompat() {
        registerTheOneProbe();
    }

    private static void registerTheOneProbe() {
        if (Loader.isModLoaded("theoneprobe")) {
            Logz.info("Trying to tell The One Probe about us");
            TopCompatibility.register();
        }
    }
}
