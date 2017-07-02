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

import org.adddxdx.graphics.GraphicsSystem;
import org.adddxdx.graphics.Light;
import org.adddxdx.math.*;

abstract class BaseLight extends GraphicsSystem.Component implements Light {
    private Color mColor = Color.WHITE;
    private float mAmbientIntensity = 0.25f;

    @Override
    public final void connect(GraphicsSystem system) {
        if (system.getLight() == null) system.setLight(this);
    }

    @Override
    public final void disconnect(GraphicsSystem system) {
        if (this.equals(system.getLight())) system.setLight(null);
    }

    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 rotationFix = Matrix4.getRotationMatrix(0, FMath.PI, 0);
        return viewMatrix.cross(rotationFix).inverse();
    }

    public Vector3 getDirection() {
        return getActor().getTransform().getViewDirection();
    }

    @Override
    public Color getColor() {
        return mColor;
    }

    @Override
    public float getAmbientIntensity() {
        return mAmbientIntensity;
    }

    void setColor(Color color) {
        mColor = color;
    }

    void setAmbientIntensity(float ambientIntensity) {
        mAmbientIntensity = ambientIntensity;
    }
}
