/*
 * Copyright (C) 2017 Poroshin Ivan
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.math.interpolators;

import org.adddxdx.math.FMath;
import org.adddxdx.math.Vector2;
import org.adddxdx.math.Vector3;
import org.adddxdx.math.Vector4;

public class Interpolators {
    private Interpolators() { }

    private static Interpolator<Float> sLinear;
    private static BiInterpolator<Float> sQuadratic;
    private static TriInterpolator<Float> sCubic;

    public static Interpolator<Float> linear() {
        if (sLinear == null) {
            sLinear = (first, second, proportion) ->
                    first * (1 - proportion) + second * proportion;
        }
        return sLinear;
    }

    public static BiInterpolator<Float> quadratic() {
        if (sQuadratic == null) {
            sQuadratic = (start, anchor, end, proportion) ->
                    start * FMath.pow(1 - proportion, 2)
                    + anchor * (1 - proportion) * proportion
                    + end * FMath.pow(proportion, 2);
        }
        return sQuadratic;
    }

    public static Interpolator<Float> quadratic(float anchor) {
        return (first, second, proportion) ->
                quadratic().interpolate(first, anchor, second, proportion);
    }

    public static TriInterpolator<Float> cubic() {
        if (sCubic == null) {
            sCubic = (start, firstAnchor, secondAnchor, end, proportion) ->
                    start * FMath.pow(1 - proportion, 3)
                    + firstAnchor * FMath.pow(1 - proportion, 2) * proportion
                    + secondAnchor * (1 - proportion) * FMath.pow(proportion, 2)
                    + end * FMath.pow(proportion, 3);
        }
        return sCubic;
    }

    public static Interpolator<Float> cubic(float firstAnchor, float secondAnchor) {
        return (first, second, proportion) ->
                cubic().interpolate(first, firstAnchor, secondAnchor, second, proportion);
    }

    public static Interpolator<Vector2> linear2() {
        return vector2(linear());
    }

    public static Interpolator<Vector2> vector2(Interpolator<Float> f) {
        return (first, second, proportion) -> new Vector2(
                f.interpolate(first.getX(), second.getX(), proportion),
                f.interpolate(first.getY(), second.getY(), proportion)
        );
    }

    public static BiInterpolator<Vector2> quadratic2() {
        return vector2(quadratic());
    }

    public static BiInterpolator<Vector2> vector2(BiInterpolator<Float> f) {
        return (first, anchor, second, proportion) -> new Vector2(
                f.interpolate(first.getX(), anchor.getX(), second.getX(), proportion),
                f.interpolate(first.getY(), anchor.getY(), second.getY(), proportion)
        );
    }

    public static TriInterpolator<Vector2> cubic2() {
        return vector2(cubic());
    }

    public static TriInterpolator<Vector2> vector2(TriInterpolator<Float> f) {
        return (start, firstAnchor, secondAnchor, end, proportion) -> new Vector2(
                f.interpolate(start.getX(), firstAnchor.getX(), secondAnchor.getX(), end.getX(), proportion),
                f.interpolate(start.getY(), firstAnchor.getY(), secondAnchor.getY(), end.getY(), proportion)
        );
    }

    public static Interpolator<Vector3> linear3() {
        return vector3(linear());
    }

    public static Interpolator<Vector3> vector3(Interpolator<Float> f) {
        return (first, second, proportion) -> new Vector3(
                f.interpolate(first.getX(), second.getX(), proportion),
                f.interpolate(first.getY(), second.getY(), proportion),
                f.interpolate(first.getZ(), second.getZ(), proportion)
        );
    }

    public static BiInterpolator<Vector3> quadratic3() {
        return vector3(quadratic());
    }

    public static BiInterpolator<Vector3> vector3(BiInterpolator<Float> f) {
        return (first, anchor, second, proportion) -> new Vector3(
                f.interpolate(first.getX(), anchor.getX(), second.getX(), proportion),
                f.interpolate(first.getY(), anchor.getY(), second.getY(), proportion),
                f.interpolate(first.getZ(), anchor.getZ(), second.getZ(), proportion)
        );
    }

    public static TriInterpolator<Vector3> cubic3() {
        return vector3(cubic());
    }

    public static TriInterpolator<Vector3> vector3(TriInterpolator<Float> f) {
        return (start, firstAnchor, secondAnchor, end, proportion) -> new Vector3(
                f.interpolate(start.getX(), firstAnchor.getX(), secondAnchor.getX(), end.getX(), proportion),
                f.interpolate(start.getY(), firstAnchor.getY(), secondAnchor.getY(), end.getY(), proportion),
                f.interpolate(start.getZ(), firstAnchor.getZ(), secondAnchor.getZ(), end.getZ(), proportion)
        );
    }

    public static Interpolator<Vector4> linear4() {
        return vector4(linear());
    }

    public static Interpolator<Vector4> vector4(Interpolator<Float> f) {
        return (first, second, proportion) -> new Vector4(
                f.interpolate(first.getX(), second.getX(), proportion),
                f.interpolate(first.getY(), second.getY(), proportion),
                f.interpolate(first.getZ(), second.getZ(), proportion),
                f.interpolate(first.getW(), second.getW(), proportion)
        );
    }

    public static BiInterpolator<Vector4> quadratic4() {
        return vector4(quadratic());
    }

    public static BiInterpolator<Vector4> vector4(BiInterpolator<Float> f) {
        return (first, anchor, second, proportion) -> new Vector4(
                f.interpolate(first.getX(), anchor.getX(), second.getX(), proportion),
                f.interpolate(first.getY(), anchor.getY(), second.getY(), proportion),
                f.interpolate(first.getZ(), anchor.getZ(), second.getZ(), proportion),
                f.interpolate(first.getW(), anchor.getW(), second.getW(), proportion)
        );
    }

    public TriInterpolator<Vector4> cubic4() {
        return vector4(cubic());
    }

    public static TriInterpolator<Vector4> vector4(TriInterpolator<Float> f) {
        return (start, firstAnchor, secondAnchor, end, proportion) -> new Vector4(
                f.interpolate(start.getX(), firstAnchor.getX(), secondAnchor.getX(), end.getX(), proportion),
                f.interpolate(start.getY(), firstAnchor.getY(), secondAnchor.getY(), end.getY(), proportion),
                f.interpolate(start.getZ(), firstAnchor.getZ(), secondAnchor.getZ(), end.getZ(), proportion),
                f.interpolate(start.getW(), firstAnchor.getW(), secondAnchor.getW(), end.getW(), proportion)
        );
    }
}
