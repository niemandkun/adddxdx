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

import org.adddxdx.clocks.Animator;
import org.adddxdx.engine.Actor;
import org.adddxdx.graphics.support.components.DirectionalLight;
import org.adddxdx.math.Color;
import org.adddxdx.math.FMath;

import java.time.Duration;

public class TestingLight extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        DirectionalLight light = new DirectionalLight();
        light.setAmbientIntensity(0.5f);

        addComponent(light);

        Duration wholeDay = Duration.ofMinutes(1);
        Duration halfDay = wholeDay.dividedBy(2);
        Duration quarterDay = wholeDay.dividedBy(4);

        addComponent(Animator
                .ofFloat(t -> {
                    light.setColor(new Color(t, t, t));
                    light.setAmbientIntensity(FMath.pow(1 - t / 2, 2));
                })
                .from(0.05f)
                .to(1f).in(quarterDay)
                .to(1f).in(quarterDay)
                .to(0.05f).in(quarterDay)
                .repeatIn(quarterDay)
                .build());

        addComponent(Animator
                .ofFloat(x -> {
                    getTransform().setRotation(FMath.HALF_PI, 0, x);
                    getTransform().rotate(0, 0, 0);
                })
                .from(-FMath.HALF_PI)
                .to(-FMath.PI).in(halfDay)
                .to(FMath.PI).immediately()
                .to(FMath.HALF_PI).in(halfDay)
                .repeat()
                .build());
    }
}
