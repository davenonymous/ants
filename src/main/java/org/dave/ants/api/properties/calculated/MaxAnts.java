package org.dave.ants.api.properties.calculated;

import org.dave.ants.api.properties.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty
public class MaxAnts extends BaseProperty<Double> {
    public MaxAnts() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return 0D;
    }
}

