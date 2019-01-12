package org.dave.ants.api.properties.calculated;

import org.dave.ants.api.properties.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty
public class TicksBetweenBabies extends BaseProperty<Integer> {
    public static int clampTicksBetweenBabies = 20;
    public static int baseTicksBetweenBabies = 20 * 5;

    public TicksBetweenBabies() {
        super(Integer.class);
    }

    @Override
    public Integer getDefault() {
        return baseTicksBetweenBabies;
    }

    @Override
    public Integer clamp(Integer value) {
        return value < clampTicksBetweenBabies ? clampTicksBetweenBabies : value;
    }
}

