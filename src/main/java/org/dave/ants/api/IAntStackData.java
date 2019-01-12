package org.dave.ants.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.dave.ants.api.chambers.IAntChamber;

import javax.annotation.Nullable;

/**
 * Do not implement this interface yourself, but get an instance of this
 * for any given stack using {@link IAntHelpers#getStackInfo(ItemStack)}.
 */
public interface IAntStackData {
    /**
     * This returns false if the given stack is not linked to an existing ant hill
     *
     * @return true if the stack is linked to a specific hill
     */
    boolean hasHillId();

    /**
     * The id this stack is linked to. Check via {@link #hasHillId()} first!
     *
     * @return the id of the hill or -1 if it does not exist.
     */
    int getHillId();

    /**
     * If this stack is indeed an ant chamber, this returns its current tier.
     *
     * @return a value between 0 and Integer.MAX_VALUE.
     */
    int getChamberTier();

    /**
     * This returns false if the given stack is not an ant chamber stack.
     *
     * @return true if this is an ant chamber
     */
    boolean hasChamberType();


    /**
     * Returns the chamber type of this ant stack. Check using {@link #hasChamberType()}
     * first!
     *
     * @return the class of the chamber.
     */
    @Nullable
    Class<? extends IAntChamber> getChamberType();

    /**
     * You should not need to access the chamber data directly, instead
     * use the chamber registry to get an instance of the chamber stored
     * in this stack with the {@link org.dave.ants.api.chambers.IChamberRegistry#getChamberInstance(IAntStackData)}
     * method.
     *
     * But if you do, check it first with this method.
     *
     * @return whether or not the given ItemStack has any chamber data associated with it.
     */
    boolean hasChamberData();

    /**
     * You should not need to access the chamber data directly, instead
     * use the chamber registry to get an instance of the chamber stored
     * in this stack with the {@link org.dave.ants.api.chambers.IChamberRegistry#getChamberInstance(IAntStackData)}
     * method.
     *
     * @return the actual chamber data.
    */
    NBTTagCompound getChamberData();
}
