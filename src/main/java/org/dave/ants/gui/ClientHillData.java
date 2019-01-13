package org.dave.ants.gui;

import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.hill.IClientHillData;
import org.dave.ants.api.properties.IHillProperty;
import org.dave.ants.util.DimPos;

import java.util.HashMap;
import java.util.Map;

public class ClientHillData implements IClientHillData {
    public Class<? extends IAntChamber> chamberType;
    public NBTTagCompound chamberData;
    public DimPos chamberPos;
    public Map<Class<? extends IHillProperty>, IHillProperty> properties = new HashMap<>();
    public Map<Class<? extends IAntChamber>, Integer> maxTierLevel = new HashMap<>();
    public long lastMessageReceived;

    @Override
    public boolean hasData() {
        return chamberType != null && chamberData != null && chamberPos != null;
    }

    public void reset() {
        chamberType = null;
        chamberData = null;
        chamberPos = null;
        properties.clear();
        maxTierLevel.clear();
    }

    @Override
    public IAntChamber getChamberInstance() {
        IAntChamber chamber = null;
        try {
            chamber = chamberType.newInstance();
            chamber.deserializeNBT(chamberData);
            return chamber;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public <V> IHillProperty<V> getProperty(Class<? extends IHillProperty<V>> propClass) {
        if(!properties.containsKey(propClass)) {
            try {
                IHillProperty property = propClass.newInstance();
                property.setValue(property.getDefault());
                properties.put(propClass, property);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return properties.get(propClass);
    }

    @Override
    public <V> V getPropertyValue(Class<? extends IHillProperty<V>> property) {
        return getProperty(property).getValue();
    }
}
