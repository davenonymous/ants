package org.dave.ants.api.properties.calculated;

import org.dave.ants.api.properties.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty
public class FoodGainPerTick extends BaseProperty<Double> {
    public FoodGainPerTick() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return 0D;
    }
}