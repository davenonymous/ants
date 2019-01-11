package org.dave.ants.actions;

import org.dave.ants.api.serialization.Store;

public class AddWorker extends BaseAction {
    @Store
    public int count;

    public AddWorker() {
    }

    public AddWorker(int count) {
        this.count = count;
    }
}
