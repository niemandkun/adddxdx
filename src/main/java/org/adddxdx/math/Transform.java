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

import org.jetbrains.annotations.NotNull;

public class Transform {
    private Vector3 mLocation;
    private Vector3 mScale;
    private Quaternion mRotation;

    public Transform() {
        mLocation = Vector3.ZERO;
        mScale = Vector3.ONE;
        mRotation = Quaternion.IDENTITY;
    }

    public void translate(float x, float y, float z) {
        translate(new Vector3(x, y, z));
    }

    public void translate(@NotNull Vector3 direction) {
        mLocation = mLocation.add(direction);
    }

    public void scale(float factor) {
        scale(new Vector3(factor, factor, factor));
    }

    public void scale(float x, float y, float z) {
        scale(new Vector3(x, y, z));
    }

    public void scale(@NotNull Vector3 v) {
        mScale = new Vector3(
                mScale.getX() * v.getX(),
                mScale.getY() * v.getY(),
                mScale.getZ() * v.getZ()
        );
    }

    public void rotate(float x, float y, float z) {
        rotate(new Vector3(x, y, z));
    }

    public void rotate(@NotNull Vector3 eulerAngles) {
        mRotation = mRotation.rotate(eulerAngles);
    }

    public void rotate(@NotNull Quaternion rotation) {
        mRotation = mRotation.dot(rotation);
    }

    public void setLocation(float x, float y, float z) {
        mLocation = new Vector3(x, y, z);
    }

    public void setLocation(@NotNull Vector3 location) {
        mLocation = location;
    }

    public void setRotation(float x, float y, float z) {
        mRotation = Quaternion.fromEulerAngles(x, y, z);
    }

    public void setRotation(@NotNull Vector3 eulerAngles) {
        mRotation = Quaternion.fromEulerAngles(eulerAngles);
    }

    public void setRotation(@NotNull Quaternion rotation) {
        mRotation = rotation;
    }

    public void setScale(float scale) {
        mScale = new Vector3(scale, scale, scale);
    }

    public void setScale(float x, float y, float z) {
        mScale = new Vector3(x, y, z);
    }

    public void setScale(@NotNull Vector3 scale) {
        mScale = scale;
    }

    @NotNull public Quaternion getRotation() { return mRotation; }
    @NotNull public Vector3 getLocation() { return mLocation; }
    @NotNull public Vector3 getScale() { return mScale; }

    @NotNull public Vector3 getViewDirection() {
        return mRotation.apply(Vector3.FORWARD);
    }

    @NotNull public Matrix4 getMatrix() {
        return Matrix4.getTranslationMatrix(mLocation)
                .cross(mRotation.getRotationMatrix())
                .cross(Matrix4.getScaleMatrix(mScale));
    }
}
