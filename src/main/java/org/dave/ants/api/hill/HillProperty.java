package org.dave.ants.api.hill;

public @interface HillProperty {
    String mod() default "";

    /**
     * Whether or not the property is stored or dynamically calculated.
     * If this returns true, the value will be saved and restored in the HillData structure.
     *
     * @return false if the value is dynamically calculated
     */
    boolean store() default false;
}
