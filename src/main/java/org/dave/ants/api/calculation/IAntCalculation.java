package org.dave.ants.api.calculation;

import org.dave.ants.hills.HillData;

/**
 * Implement this interface and annotate it with @{@link AntCalculation} to add
 * custom calculations to each hill tick.
 *
 * This allows you to manipulate the hill properties without large amounts of
 * ticking tile entities.
 */
public interface IAntCalculation {
    /**
     * Calculations are performed in a specific order. You can use the priority value
     * you return here to influence when your calculation will take place.
     *
     * The lower the value the earlier it will be executed.
     * Only put clamping calculations at Integer.MAX_VALUE!
     *
     * @return the priority, determining when this calculation is being performed.
     */
    int priority();

    /**
     * Perform the actual calculation.
     * Modify the HillData object to your liking. Don't do anything the user
     * would not expect.
     *
     * @param data The current state of the hill that's currently being calculated.
     * @param currentWorldTick For convenience reasons, there current world tick
     */
    void calculate(HillData data, long currentWorldTick);
}
