package org.dave.ants.api.properties.stored;

import org.dave.ants.api.properties.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty(store = true)
public class UsableAnts extends BaseProperty<Double> {
    public UsableAnts() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return 0D;
    }
}
