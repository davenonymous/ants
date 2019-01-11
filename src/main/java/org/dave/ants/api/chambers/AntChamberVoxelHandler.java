package org.dave.ants.api.chambers;

public @interface AntChamberVoxelHandler {
    Class antChamberType() default Class.class;
}
