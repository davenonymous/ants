package org.dave.ants.actions;

import org.dave.ants.api.actions.IAntGuiAction;
import org.dave.ants.gui.ClientChamberGuiCache;
import org.dave.ants.network.AntsNetworkHandler;
import org.dave.ants.network.ChamberActionMessage;
import org.dave.ants.network.HillActionMessage;

public class ActionRegistry implements org.dave.ants.api.actions.IActionRegistry {
    @Override
    public void fireChamberAction(IAntGuiAction action) {
        AntsNetworkHandler.instance.sendToServer(new ChamberActionMessage(ClientChamberGuiCache.chamberPos, action));
    }

    @Override
    public void fireHillAction(IAntGuiAction action) {
        AntsNetworkHandler.instance.sendToServer(new HillActionMessage(ClientChamberGuiCache.chamberPos, action));
    }
}
