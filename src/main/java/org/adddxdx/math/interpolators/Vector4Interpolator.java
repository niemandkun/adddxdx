package org.adddxdx.math.interpolators;

import org.adddxdx.math.Vector4;

public class Vector4Interpolator implements Interpolator<Vector4> {
    private final FloatInterpolator mFloatInterpolator = new FloatInterpolator();

    @Override
    public Vector4 interpolate(Vector4 first, Vector4 second, float proportion) {
        return new Vector4(
                mFloatInterpolator.interpolate(first.getX(), second.getX(), proportion),
                mFloatInterpolator.interpolate(first.getY(), second.getY(), proportion),
                mFloatInterpolator.interpolate(first.getZ(), second.getZ(), proportion),
                mFloatInterpolator.interpolate(first.getW(), second.getW(), proportion)
        );
    }
}
