package org.dave.ants.api.hill;

import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.properties.IHillProperty;
import org.dave.ants.api.properties.stored.UsableAnts;

/**
 * Do not implement this, you get a reference to it in your {@link org.dave.ants.api.IAntPlugin}.
 *
 * This is a similiar data structure to {@link IHillData}, but available on the client. It is
 * purely meant for GUI interactions and only updated if and when the player has an ant hill gui
 * opened. Then it is synced every second by default (this might be configurable or change, don't
 * count on it.)
 *
 * Make sure to check any accesses with hasData().
 *
 * While you could change properties via the {@link #getProperty(Class)} method this will have no
 * effect, but garbled values in the GUI.
 */
public interface IClientHillData {
    /**
     * Use this method to test whether the client has data stored before using any of the other
     * methods. It might take a moment on the client after opening the GUI before he has actually
     * received the hill data.
     *
     * On the other hand, if you are only accessing this data structure in your {@link IAntChamber#createGuiPanel(int)}
     * method it is already ensured this data structure is filled otherwise you would not get to
     * that method call.
     *
     * @return whether the client has data stored about the hill which currently has a gui open
     */
    boolean hasData();

    /**
     * Gets a client-side instance of the chamber the player is currently having a GUI open for.
     * You should not be needing this. If you do, make sure you only call methods that are available
     * and meaningful on the client. Remember, this is a client side data structure. You can not
     * change the World with it ;)
     *
     * It is internally only used to determine whether the relevant chamber has a GUI to be rendered
     * and what its description is.
     *
     * @return an instance of the chamber the player is looking at via GUI
     */
    IAntChamber getChamberInstance();

    /**
     * Retrieve an {@link IHillProperty} of the ant hill the player is having a GUI open for.
     * You should not need this and should probably be using {@link #getPropertyValue(Class)} instead!
     *
     * If you use this to modify values, you are only doing so on the client side - the gui rendering
     * will be all that is affected. Look into the {@link IHillData} and registries providing it to
     * actually modify values on the server!
     *
     * @param propClass The class of the property you want to receive
     * @param <V>       The type the property stores
     * @return an instance of the IHillProperty
     */
    <V> IHillProperty<V> getProperty(Class<? extends IHillProperty<V>> propClass);

    /**
     * Retrieve the value of an {@link IHillProperty} the player is having a GUI open for.
     * You can use this to e.g. look up the current amount of ants using {@link UsableAnts}
     *
     * @param property The class of the property you want to receive
     * @param <V>      The type the property stores
     * @return the value of the given property
     */
    <V> V getPropertyValue(Class<? extends IHillProperty<V>> property);
}
