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
import org.adddxdx.math.*;

public class PerspectiveCamera extends Camera {
    private static final float DEFAULT_NEAR_PLANE = 0.3f;
    private static final float DEFAULT_FAR_PLANE = 1000f;
    private static final float DEFAULT_ASPECT_RATIO = 16 / 9f;
    private static final float DEFAULT_FIELD_OF_VIEW = FMath.PI / 3;

    private float mNearPlane;
    private float mFarPlane;
    private float mFieldOfView;
    private float mAspectRatio;

    public PerspectiveCamera() {
        mAspectRatio = DEFAULT_ASPECT_RATIO;
        mNearPlane = DEFAULT_NEAR_PLANE;
        mFarPlane = DEFAULT_FAR_PLANE;
        mFieldOfView = DEFAULT_FIELD_OF_VIEW;
    }

    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 cameraTransform = Matrix4.getRotationMatrix(0, FMath.PI, 0);
        return viewMatrix.cross(cameraTransform).inverse();
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        return Projection.perspective(mFieldOfView, mAspectRatio, mNearPlane, mFarPlane);
    }

    @Override
    public void adjustAspectRatio(Size targetSize) {
        mAspectRatio = (float) targetSize.getWidth() / targetSize.getHeight();
    }

    float getAspectRatio() {
        return mAspectRatio;
    }

    float getNearPlane() {
        return mNearPlane;
    }

    void setNearPlane(float nearPlane) {
        mNearPlane = nearPlane;
    }

    float getFarPlane() {
        return mFarPlane;
    }

    void setFarPlane(float farPlane) {
        mFarPlane = farPlane;
    }

    float getFieldOfView() {
        return mFieldOfView;
    }

    void setFieldOfView(float fieldOfView) {
        mFieldOfView = fieldOfView;
    }
}
