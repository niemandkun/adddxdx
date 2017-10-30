package org.adddxdx.math.interpolators;

public class DoubleInterpolator implements Interpolator<Double> {
    @Override
    public Double interpolate(Double first, Double second, float proportion) {
        return first * proportion + second * (1 - proportion);
    }
}
