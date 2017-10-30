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
import org.adddxdx.graphics.support.components.MeshSkin;
import org.adddxdx.graphics.support.materials.DefaultMaterial;
import org.adddxdx.graphics.support.primitives.PrimitiveType;
import org.adddxdx.math.FMath;
import org.adddxdx.math.Vector3;

import java.time.Duration;

public class PieceOfGlass extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        DefaultMaterial material = getResources().getMaterial(DefaultMaterial.class);
        material.setTexture(getResources().getTexture("textures/glass.png"));

        MeshSkin meshSkin = new MeshSkin(getResources().getPrimitive(PrimitiveType.QUAD), material);
        meshSkin.setCastShadows(false);

        addComponent(meshSkin);

        Vector3[] wayPoints = {
                new Vector3(2, 1, 1),
                new Vector3(-2, 1, 1),
                new Vector3(-2, 1, -1),
                new Vector3(2, 1, -1),
        };

        addComponent(Animator.ofVector3(getTransform()::setLocation)
                .from(wayPoints[0])
                .to(wayPoints[1]).in(Duration.ofSeconds(1))
                .to(wayPoints[2]).in(Duration.ofSeconds(1))
                .to(wayPoints[3]).in(Duration.ofSeconds(1))
                .repeatIn(Duration.ofSeconds(1))
                .build());

        getTransform().rotate(0, FMath.PI, 0);
    }
}
