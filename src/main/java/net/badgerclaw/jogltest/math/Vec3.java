package net.badgerclaw.jogltest.math;

public class Vec3 extends AbstractVector<Vec3, Dimensionality3> implements Dimensionality3, Cloneable {

    public final static int
            X = 0,
            Y = 1,
            Z = 2;

    public Vec3() {
    }

    public Vec3(float x, float y, float z) {
        v[X] = x;
        v[Y] = y;
        v[Z] = z;
    }

    @Override
    public Vec3 clone() {
        return copyTo(new Vec3());
    }

}
