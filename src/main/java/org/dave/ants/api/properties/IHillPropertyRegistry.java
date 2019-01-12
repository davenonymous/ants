package org.dave.ants.api.properties;

public interface IHillPropertyRegistry {
    void registerHillProperty(Class<? extends IHillProperty> hillPropertyClass, boolean store);
}
