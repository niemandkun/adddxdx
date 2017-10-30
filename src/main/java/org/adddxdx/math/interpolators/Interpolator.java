package org.adddxdx.math.interpolators;

public interface Interpolator<T> {
    T interpolate(T first, T second, float proportion);
}
