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

package org.adddxdx.briefexample;

import org.adddxdx.engine.Actor;
import org.adddxdx.graphics.support.components.Fog;
import org.adddxdx.graphics.support.components.OrthoCamera;
import org.adddxdx.graphics.support.components.PerspectiveCamera;
import org.adddxdx.io.*;
import org.adddxdx.math.*;

import java.time.Duration;

public class Player extends Actor {

    private static final float CAMERA_X_ANGLE = -FMath.PI / 6;
    private static final float CAMERA_Y_ANGLE = 7 * FMath.PI / 8;
    private static final float CAMERA_Z_ANGLE = 0;

    private static final int CAMERA_X_POS = 8;
    private static final int CAMERA_Y_POS = 12;
    private static final int CAMERA_Z_POS = -15;

    @Override
    public void onCreate() {
        super.onCreate();

        PerspectiveCamera camera = new PerspectiveCamera();
        addComponent(camera);

        Fog fog = new Fog();
        fog.setDensity(0.04f);
        fog.setExtinction(0.4f);
        addComponent(fog);

        final Transform transform = getTransform();
        transform.translate(CAMERA_X_POS, CAMERA_Y_POS, CAMERA_Z_POS);
        transform.setRotation(CAMERA_X_ANGLE, CAMERA_Y_ANGLE, CAMERA_Z_ANGLE);

        addComponent(new KeyboardController() {
            float mCameraAngle = CAMERA_Y_ANGLE;
            float mCameraSpeed = 10f / 1000;

            @Override public void checkKeyboardState(Duration delta, Keyboard keyboard) {
                Vector3 forward = transform.getViewDirection().setY(0).normalize().mul(mCameraSpeed * delta.toMillis());
                Vector3 backward = forward.negate();
                Vector3 left = forward.rotateAroundOy(FMath.PI / 2);
                Vector3 right = left.negate();

                if (keyboard.isPressed(Key.W)) transform.translate(forward);
                if (keyboard.isPressed(Key.S)) transform.translate(backward);
                if (keyboard.isPressed(Key.A)) transform.translate(left);
                if (keyboard.isPressed(Key.D)) transform.translate(right);

                if (keyboard.isPressed(Key.Q)) mCameraAngle += 0.001f * delta.toMillis();
                if (keyboard.isPressed(Key.E)) mCameraAngle -= 0.001f * delta.toMillis();

                transform.setRotation(CAMERA_X_ANGLE, mCameraAngle, CAMERA_Z_ANGLE);
            }
        });
    }
}
