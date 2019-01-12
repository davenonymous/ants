package org.dave.ants.api.properties;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHillProperty<T> extends INBTSerializable<NBTTagCompound> {
    Class<T> getType();
    T getValue();
    void setValue(T value);
    T getDefault();

    default T clamp(T value) {
        return value;
    }
}
