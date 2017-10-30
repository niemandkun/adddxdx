package org.adddxdx.math.interpolators;

import org.adddxdx.math.Vector3;

public class Vector3Interpolator implements Interpolator<Vector3> {
    private final FloatInterpolator mFloatInterpolator = new FloatInterpolator();

    @Override
    public Vector3 interpolate(Vector3 first, Vector3 second, float proportion) {
        return new Vector3(
                mFloatInterpolator.interpolate(first.getX(), second.getX(), proportion),
                mFloatInterpolator.interpolate(first.getY(), second.getY(), proportion),
                mFloatInterpolator.interpolate(first.getZ(), second.getZ(), proportion)
        );
    }
}
