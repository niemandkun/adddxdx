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

package org.adddxdx.graphics;

import org.adddxdx.math.FMath;
import org.adddxdx.math.Matrix4;
import org.adddxdx.math.Size;
import org.adddxdx.math.Vector3;

public abstract class Camera extends GraphicsSystem.Component implements View {
    @Override
    public final void connect(GraphicsSystem system) {
        if (system.getCamera() == null) system.setCamera(this);
    }

    @Override
    public final void disconnect(GraphicsSystem system) {
        if (this.equals(system.getCamera())) system.setCamera(null);
    }

    @Override
    public Vector3 getLocation() {
        return getActor().getTransform().getLocation();
    }

    @Override
    public Vector3 getDirection() {
        return getActor().getTransform().getViewDirection();
    }

    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 cameraTransform = Matrix4.getRotationMatrix(0, FMath.PI, 0);
        return viewMatrix.cross(cameraTransform).inverse();
    }

    public abstract void adjustAspectRatio(Size targetSize);
}
