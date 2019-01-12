package org.dave.ants.api.hill;

import java.util.function.Function;

/**
 * Do not implement this interface. You are getting instances of this passed in
 * various other implementations of yours.
 */
public interface IHillData {
    <V> IHillProperty<V> getProperty(Class<? extends IHillProperty<V>> propClass);

    <V> V getPropertyValue(Class<? extends IHillProperty<V>> property);

    <V> void setPropertyValue(Class<? extends IHillProperty<V>> property, V value);

    <V> void modifyPropertyValue(Class<? extends IHillProperty<V>> property, Function<V, V> func);

    void updateHillStatistics();
}
