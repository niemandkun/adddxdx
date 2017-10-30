package org.adddxdx.math.interpolators;

import org.adddxdx.math.Vector2;

public class Vector2Interpolator implements Interpolator<Vector2> {
    private final FloatInterpolator mFloatInterpolator = new FloatInterpolator();

    @Override
    public Vector2 interpolate(Vector2 first, Vector2 second, float proportion) {
        return new Vector2(
                mFloatInterpolator.interpolate(first.getX(), second.getX(), proportion),
                mFloatInterpolator.interpolate(first.getY(), second.getY(), proportion)
        );
    }
}
