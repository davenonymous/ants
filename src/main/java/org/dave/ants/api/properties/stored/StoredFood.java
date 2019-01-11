package org.dave.ants.api.properties.stored;

import org.dave.ants.api.hill.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty(store = true)
public class StoredFood extends BaseProperty<Double> {
    public StoredFood() {
        super(Double.class);
    }

    @Override
    public Double getDefault() {
        return 0D;
    }

    @Override
    public Double clamp(Double value) {
        return value <= 0 ? 0.0d : value;
    }
}
