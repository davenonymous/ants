package org.dave.ants.api.serialization;

public @interface SerializationHandler {
    Class readClass() default void.class;
    Class writeClass() default void.class;
}
