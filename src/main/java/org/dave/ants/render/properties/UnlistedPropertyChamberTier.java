package org.dave.ants.render.properties;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyChamberTier implements IUnlistedProperty<Integer> {
    @Override
    public String getName() {
        return "chamberTier";
    }

    @Override
    public boolean isValid(Integer value) {
        return value >= 0;
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public String valueToString(Integer value) {
        return value.toString();
    }
}
