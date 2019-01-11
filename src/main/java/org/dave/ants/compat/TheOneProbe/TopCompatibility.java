package org.dave.ants.compat.TheOneProbe;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.dave.ants.util.Logz;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TopCompatibility {
    private static boolean registered;

    public static void register() {
        if (registered) {
            return;
        }

        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "org.dave.ants.compat.TheOneProbe.TopCompatibility$GetTheOneProbe");
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
        public static ITheOneProbe probe;

        /**
         * Applies this function to the given argument.
         *
         * @param iTheOneProbe the function argument
         * @return the function result
         */
        @Nullable
        @Override
        public Void apply(ITheOneProbe iTheOneProbe) {
            probe = iTheOneProbe;
            Logz.info("Enabled support for The One Probe");
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID() {
                    return "ants:default";
                }

                @Override
                public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
                    if (blockState.getBlock() instanceof ITopInfoProvider) {
                        ITopInfoProvider provider = (ITopInfoProvider) blockState.getBlock();
                        provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
                    }
                }
            });
            return null;
        }
    }
}
