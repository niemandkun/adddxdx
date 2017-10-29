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
import org.adddxdx.graphics.Light;
import org.adddxdx.graphics.support.components.DirectionalLight;
import org.adddxdx.io.Mouse;
import org.adddxdx.io.MouseController;
import org.adddxdx.io.MouseEvent;
import org.adddxdx.math.FMath;

public class TestingLight extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        DirectionalLight light = new DirectionalLight();
        light.setAmbientIntensity(0.5f);

        addComponent(light);
        getTransform().setRotation(0.4f, 0, 0);
        getTransform().rotate(0, FMath.PI, 0);

        addComponent(new MouseController() {
            float rotationY = FMath.PI;

            @Override public void onPointerMoved(Mouse mouse, MouseEvent event) {
                rotationY -= event.getPointerMovement().getX() / 200;

                getTransform().setRotation(0.4f, 0, 0);
                getTransform().rotate(0, rotationY, 0);
            }
        });
    }
}
