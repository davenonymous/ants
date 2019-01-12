package org.dave.ants.network;

import org.dave.ants.api.actions.IAntGuiAction;
import org.dave.ants.util.DimPos;

public class HillActionMessage extends ChamberActionMessage {
    public HillActionMessage() {
    }

    public HillActionMessage(DimPos pos, IAntGuiAction action) {
        super(pos, action);
    }
}
