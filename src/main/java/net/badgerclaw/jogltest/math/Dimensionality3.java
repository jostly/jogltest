package net.badgerclaw.jogltest.math;

public interface Dimensionality3 extends Dimensionality {

    @Override
    default int dimensions() { return 3; }
}
