package org.dave.ants.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.properties.IHillProperty;
import org.dave.ants.util.serialization.FieldHandlers;

import java.util.Map;

public class ChamberDataMessageHandler implements IMessageHandler<ChamberDataMessage, ChamberDataMessage> {
    @SuppressWarnings("unchecked")
    @Override
    public ChamberDataMessage onMessage(ChamberDataMessage message, MessageContext ctx) {
        Ants.clientHillData.chamberData = message.chamberData;
        Ants.clientHillData.chamberType = message.type;
        Ants.clientHillData.chamberPos = message.pos;

        Map<Class<? extends IHillProperty>, IHillProperty> properties = (Map<Class<? extends IHillProperty>, IHillProperty>) FieldHandlers.getNBTHandler(Ants.clientHillData.properties.getClass()).getLeft().read("properties", message.properties);
        Map<Class<? extends IHillProperty>, IHillProperty> calculated = (Map<Class<? extends IHillProperty>, IHillProperty>) FieldHandlers.getNBTHandler(Ants.clientHillData.properties.getClass()).getLeft().read("calculated", message.properties);
        Ants.clientHillData.properties.clear();
        Ants.clientHillData.properties.putAll(properties);
        Ants.clientHillData.properties.putAll(calculated);

        Map<Class<? extends IAntChamber>, Integer> maxTierLevels = (Map<Class<? extends IAntChamber>, Integer>) FieldHandlers.getNBTHandler(Ants.clientHillData.maxTierLevel.getClass()).getLeft().read("maxTierLevels", message.maxTierLevels);

        Ants.clientHillData.maxTierLevel.clear();
        Ants.clientHillData.maxTierLevel.putAll(maxTierLevels);

        Ants.clientHillData.lastMessageReceived = System.currentTimeMillis();
        return null;
    }
}
