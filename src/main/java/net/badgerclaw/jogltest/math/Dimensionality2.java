package net.badgerclaw.jogltest.math;

public interface Dimensionality2 extends Dimensionality {

    @Override
    default int dimensions() { return 2; }
}
