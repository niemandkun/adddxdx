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

package org.adddxdx.graphics.support.components;

import org.adddxdx.graphics.Camera;
import org.adddxdx.math.Matrix4;
import org.adddxdx.math.Projection;
import org.adddxdx.math.Size;

public class OrthoCamera extends Camera {
    private static final float DEFAULT_NEAR_PLANE = -1f;
    private static final float DEFAULT_FAR_PLANE = 100f;
    private static final float DEFAULT_HEIGHT = 2;

    private float mNearPlane = DEFAULT_NEAR_PLANE;
    private float mFarPlane = DEFAULT_FAR_PLANE;

    private float mHalfWidth = 0;
    private float mHalfHeight = DEFAULT_HEIGHT / 2;

    private Size mLastTargetSize = null;

    @Override
    public void adjustAspectRatio(Size targetSize) {
        if (targetSize == null) {
            return;
        }
        mHalfWidth = mHalfHeight * targetSize.getAspectRatio();
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        return Projection.ortho(-mHalfWidth, mHalfWidth, -mHalfHeight, mHalfHeight, mNearPlane, mFarPlane);
    }

    public void setNearPlane(float nearPlane) {
        mNearPlane = nearPlane;
    }

    public void setFarPlane(float farPlane) {
        mFarPlane = farPlane;
    }

    public void setHeight(float height) {
        mHalfHeight = height / 2;
        adjustAspectRatio(mLastTargetSize);
    }
}
