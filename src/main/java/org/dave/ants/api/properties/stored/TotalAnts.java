package org.dave.ants.api.properties.stored;

import org.dave.ants.api.properties.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty(store = true)
public class TotalAnts extends BaseProperty<Double> {
    public TotalAnts() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return 0D;
    }
}
