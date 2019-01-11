package org.dave.ants.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.dave.ants.Ants;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IChamberAction;
import org.dave.ants.hills.HillData;
import org.dave.ants.util.DimPos;

public class HillActionMessageHandler implements IMessageHandler<HillActionMessage, HillActionMessage> {
    @Override
    public HillActionMessage onMessage(HillActionMessage message, MessageContext ctx) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> handle(message, ctx));
        return null;
    }

    private void handle(HillActionMessage message, MessageContext context) {
        EntityPlayerMP player = context.getServerHandler().player;
        DimPos pos = message.pos;
        try {
            IChamberAction action = message.actionType.newInstance();
            action.deserializeNBT(message.actionData);

            HillData data = Ants.savedData.hills.getHillDataByPosition(pos);
            data.onHillAction(player, action);

            IAntChamber chamber = data.chambers.get(pos);
            AntsNetworkHandler.instance.sendTo(new ChamberDataMessage(chamber.getClass(), chamber.serializeNBT(), data.getPropertiesTag(), pos), player);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
