package org.dave.ants.actions;

import org.dave.ants.util.serialization.Store;

public class AddWorker extends BaseAction {
    @Store
    public double count;

    public AddWorker() {
    }

    public AddWorker(double count) {
        this.count = count;
    }
}
