package org.dave.ants.actions;

import org.dave.ants.api.chambers.IChamberAction;
import org.dave.ants.gui.ClientChamberGuiCache;
import org.dave.ants.network.AntsNetworkHandler;
import org.dave.ants.network.ChamberActionMessage;
import org.dave.ants.network.HillActionMessage;

public class ActionRegistry {
    public static void fireChamberAction(IChamberAction action) {
        AntsNetworkHandler.instance.sendToServer(new ChamberActionMessage(ClientChamberGuiCache.chamberPos, action));
    }

    public static void fireHillAction(IChamberAction action) {
        AntsNetworkHandler.instance.sendToServer(new HillActionMessage(ClientChamberGuiCache.chamberPos, action));
    }
}
