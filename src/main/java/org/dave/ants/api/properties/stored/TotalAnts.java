package org.dave.ants.api.properties.stored;

import org.dave.ants.api.properties.BaseProperty;
import org.dave.ants.api.properties.HillProperty;

@HillProperty(store = true)
public class TotalAnts extends BaseProperty<Double> {
    public TotalAnts() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return 0.0d;
    }

    @Override
    public Double clamp(Double value) {
        return value < 0 ? 0 : value;
    }
}
