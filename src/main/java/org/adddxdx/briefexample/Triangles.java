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
import org.adddxdx.graphics.Mesh;
import org.adddxdx.graphics.support.components.MeshSkin;
import org.adddxdx.graphics.support.materials.DefaultMaterial;
import org.adddxdx.math.Color;
import org.adddxdx.math.FMath;
import org.adddxdx.math.Vector3;
import org.adddxdx.math.interpolators.Interpolators;

import java.time.Duration;

public class Triangles extends Actor {

    @Override
    public void onCreate() {
        super.onCreate();

        Vector3[] vertices = {
                new Vector3(-0.90f, -0.90f, 0),
                new Vector3(0.85f, -0.90f, 0),
                new Vector3(-0.90f, 0.85f, 0),
                new Vector3(0.90f, -0.85f, 0),
                new Vector3(0.90f, 0.90f, 0),
                new Vector3(-0.85f, 0.90f, 0),

                new Vector3(-0.90f, -0.90f, 0),
                new Vector3(-0.90f, 0.85f, 0),
                new Vector3(0.85f, -0.90f, 0),
                new Vector3(0.90f, -0.85f, 0),
                new Vector3(-0.85f, 0.90f, 0),
                new Vector3(0.90f, 0.90f, 0),
        };

        Vector3[] normals = {
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),

                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
        };

        DefaultMaterial material = getResources().getMaterial(DefaultMaterial.class);
        material.setColor(Color.ORANGE);

        addComponent(new MeshSkin(new Mesh(vertices, normals), material));

        addComponent(Animator.ofFloat(y -> getTransform().setRotation(0, y, 0))
                .using(Interpolators.cubic(10 * FMath.PI, -10 * FMath.PI))
                .from(0f).to(2 * FMath.PI).in(Duration.ofSeconds(4)).repeat().build());
    }
}
