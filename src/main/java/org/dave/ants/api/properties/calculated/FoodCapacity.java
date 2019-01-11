package org.dave.ants.api.properties.calculated;

import org.dave.ants.api.hill.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty
public class FoodCapacity extends BaseProperty<Double> {
    public FoodCapacity() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return 0D;
    }
}

