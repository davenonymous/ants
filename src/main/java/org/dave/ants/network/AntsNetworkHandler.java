package org.dave.ants.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.dave.ants.Ants;

public class AntsNetworkHandler {
    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Ants.MODID);

    public static void init() {
        instance.registerMessage(ChamberDataMessageHandler.class, ChamberDataMessage.class, 0, Side.CLIENT);
        instance.registerMessage(ChamberActionMessageHandler.class, ChamberActionMessage.class, 1, Side.SERVER);
        instance.registerMessage(HillActionMessageHandler.class, HillActionMessage.class, 2, Side.SERVER);
    }
}
