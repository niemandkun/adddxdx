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
import org.adddxdx.graphics.support.components.PerspectiveCamera;
import org.adddxdx.io.*;
import org.adddxdx.math.*;

public class Player extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();
        addComponent(new PerspectiveCamera());
        addComponent(new Fog());

        final Transform transform = getTransform();
        transform.translate(0, 1, 0);

        addComponent(new KeyboardController() {
            @Override public void checkKeyboardState(Keyboard keyboard) {
                Vector3 forward = transform.getViewDirection().setY(0).normalize().div(8);
                Vector3 backward = forward.negate();
                Vector3 left = forward.rotateAroundOy(FMath.PI / 2);
                Vector3 right = left.negate();

                if (keyboard.isPressed(Key.W)) transform.translate(forward);
                if (keyboard.isPressed(Key.S)) transform.translate(backward);
                if (keyboard.isPressed(Key.A)) transform.translate(left);
                if (keyboard.isPressed(Key.D)) transform.translate(right);
            }
        });

        addComponent(new MouseController() {
            float rotationY;
            float rotationX;
            float maxRotationX = FMath.PI / 2 - 0.001f;

            @Override public void onPointerMoved(Mouse mouse, MouseEvent event) {
                rotationX = FMath.clamp(rotationX + event.getPointerMovement().getY() / 200, -maxRotationX, maxRotationX);
                rotationY -= event.getPointerMovement().getX() / 200;

                transform.setRotation(Vector3.ZERO);
                transform.rotate(0, rotationY, 0);
                transform.rotate(rotationX, 0, 0);
            }
        });
    }
}
