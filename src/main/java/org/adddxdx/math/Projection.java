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

import com.sun.istack.internal.NotNull;

public class Projection {
    public static @NotNull Matrix4 ortho(float left, float right, float bottom, float top, float near, float far) {
        float rightToLeft = right - left;
        float topToBottom = top - bottom;
        float farToNear = far - near;

        return new Matrix4(
                2 / rightToLeft, 0, 0, -(right + left) / rightToLeft,
                0, 2 / topToBottom, 0, -(top + bottom) / topToBottom,
                0, 0, -2 / farToNear, -(far + near) / farToNear,
                0, 0, 0, 1
        );
    }

    public static @NotNull Matrix4 ortho2d(float left, float right, float bottom, float top) {
        return ortho(left, right, bottom, top, -1, 1);
    }

    public static @NotNull Matrix4 perspective(float fovY, float aspect, float zNear, float zFar) {
        float f = 1.0f / (float) Math.tan(fovY / 2);

        return new Matrix4(
                f/aspect, 0, 0, 0,
                0, f, 0, 0,
                0, 0, (zFar + zNear)/(zNear - zFar), (2 * zFar * zNear)/(zNear - zFar),
                0, 0, -1,0
        );
    }

    public static @NotNull Matrix4 frustum(float left, float right, float bottom, float top, float near, float far) {
        float a11 = 2 * near / (right - left);
        float a22 = 2 * near / (top - bottom);
        float a13 = (right + left) / (right - left);
        float a23 = (top + bottom) / (top - bottom);
        float a33 = (far + near) / (near - far);
        float a34 = (2 * far * near) / (near - far);
        float a43 = -1;

        return new Matrix4(
                a11, 0, a13, 0,
                0, a22, a23, 0,
                0, 0, a33, a34,
                0, 0, a43, 0
        );
    }
}
