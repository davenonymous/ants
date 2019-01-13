package org.dave.ants.actions;

import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.util.serialization.Store;

public class BuyChamber extends BaseAction {
    @Store
    public Class<? extends IAntChamber> type;

    @Store
    public int tier;

    public BuyChamber() {
    }

    public BuyChamber(Class<? extends IAntChamber> type, int tier) {
        this.type = type;
        this.tier = tier;
    }
}
