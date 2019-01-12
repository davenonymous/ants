package org.dave.ants.api.properties;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import org.dave.ants.api.serialization.Store;
import org.dave.ants.util.serialization.FieldHandlers;

public abstract class BaseProperty<T>  implements IHillProperty<T>, INBTSerializable<NBTTagCompound> {
    @Store
    private final Class<T> type;

    @Store
    private T value;

    public BaseProperty(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        if(!FieldHandlers.hasNBTHandler(getType())) {
            return new NBTTagCompound();
        }

        NBTTagCompound result = new NBTTagCompound();
        FieldHandlers.getNBTHandler(getType()).getRight().write("value", value, result);
        return result;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(!FieldHandlers.hasNBTHandler(getType())) {
            return;
        }

        this.value = (T) FieldHandlers.getNBTHandler(getType()).getLeft().read("value", nbt);
    }
}
