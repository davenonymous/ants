package org.dave.ants.render.properties;

import net.minecraftforge.common.property.IUnlistedProperty;
import org.dave.ants.api.chambers.IAntChamber;

public class UnlistedPropertyChamberType implements IUnlistedProperty<Class> {
    @Override
    public String getName() {
        return "chamberType";
    }

    @Override
    public boolean isValid(Class value) {
        return IAntChamber.class.isAssignableFrom(value);
    }

    @Override
    public Class getType() {
        return Class.class;
    }

    @Override
    public String valueToString(Class value) {
        return value.getName();
    }
}
