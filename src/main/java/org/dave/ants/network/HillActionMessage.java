package org.dave.ants.network;

import org.dave.ants.api.chambers.IChamberAction;
import org.dave.ants.util.DimPos;

public class HillActionMessage extends ChamberActionMessage {
    public HillActionMessage() {
    }

    public HillActionMessage(DimPos pos, IChamberAction action) {
        super(pos, action);
    }
}
