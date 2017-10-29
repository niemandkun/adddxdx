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

package org.adddxdx.math;

public class FMath {
    public static final float E = (float) Math.E;
    public static final float PI = (float) Math.PI;
    public static final float HALF_PI = (float) Math.PI / 2;
    public static final float TWO_PI = 2 * (float) Math.PI;

    public static float clamp(float source, float min, float max) {
        if (source < min) return min;
        if (source > max) return max;
        return source;
    }

    public static float cos(float x) {
        return (float) Math.cos(x);
    }

    public static float sin(float x) {
        return (float) Math.sin(x);
    }
}
