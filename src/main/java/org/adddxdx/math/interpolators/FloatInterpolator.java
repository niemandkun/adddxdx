package org.adddxdx.math.interpolators;

public class FloatInterpolator implements Interpolator<Float> {
    @Override
    public Float interpolate(Float first, Float second, float proportion) {
        return first * proportion + second * (1 - proportion);
    }
}
