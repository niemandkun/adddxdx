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
import org.adddxdx.graphics.Mesh;
import org.adddxdx.graphics.support.components.MeshSkin;
import org.adddxdx.graphics.support.materials.DefaultMaterial;
import org.adddxdx.math.Vector2;
import org.adddxdx.math.Vector3;

public class Floor extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        Vector3[] vertices = {
                new Vector3(-100, 0, 100),
                new Vector3(100, 0, 100),
                new Vector3(100, 0, -100),
                new Vector3(-100, 0, 100),
                new Vector3(100, 0, -100),
                new Vector3(-100, 0, -100),
        };

        Vector3[] normals = {
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
        };

        Vector2[] uvs = {
                new Vector2(-10, 10),
                new Vector2(10, 10),
                new Vector2(10, -10),
                new Vector2(-10, 10),
                new Vector2(10, -10),
                new Vector2(-10, -10),
        };

        DefaultMaterial material = getResources().getMaterial(DefaultMaterial.class);
        material.setTexture(getResources().getTexture("textures/dust.jpg"));

        addComponent(new MeshSkin(new Mesh(vertices, normals, uvs), material));
    }
}
