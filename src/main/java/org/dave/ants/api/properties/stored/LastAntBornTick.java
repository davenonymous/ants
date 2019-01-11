package org.dave.ants.api.properties.stored;

import org.dave.ants.api.hill.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty(store = true)
public class LastAntBornTick extends BaseProperty<Long> {
    public LastAntBornTick() {
        super(Long.class);
    }

    @Override
    public Long getDefault() {
        return Long.MIN_VALUE;
    }
}
