package org.dave.ants.api.properties.calculated;

import org.dave.ants.api.properties.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty
public class FoodRequirementPerAnt extends BaseProperty<Double> {
    public static double baseFoodRequirementPerAnt = 0.02d;

    public FoodRequirementPerAnt() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return baseFoodRequirementPerAnt;
    }
}

