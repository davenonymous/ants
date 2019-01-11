package org.dave.ants.api.properties.calculated;

import org.dave.ants.api.hill.HillProperty;
import org.dave.ants.api.properties.BaseProperty;

@HillProperty
public class TotalQueens extends BaseProperty<Long> {
    public TotalQueens() {
        super(Long.class);
    }

    @Override
    public Long getDefault() {
        return 0L;
    }
}
