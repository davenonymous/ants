package org.dave.ants.util.serialization;

public @interface SerializationHandler {
    Class readClass() default void.class;
    Class writeClass() default void.class;
}
