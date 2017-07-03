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

package org.adddxdx.graphics.support.primitives;

import org.adddxdx.graphics.Mesh;
import org.adddxdx.math.Vector2;
import org.adddxdx.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class PrimitivesFactory {
    private final Map<PrimitiveType, Mesh> mInstances;

    public PrimitivesFactory() {
        mInstances = new HashMap<>();
    }

    public Mesh create(PrimitiveType primitiveType) {
        if (mInstances.containsKey(primitiveType))
            return mInstances.get(primitiveType);

        Mesh mesh = createNew(primitiveType);
        mInstances.put(primitiveType, mesh);
        return mesh;
    }

    private Mesh createNew(PrimitiveType primitiveType) {
        switch (primitiveType) {
            case QUAD: return createQuad();
            case CYLINDER: return createCylinder();
        }

        throw new UnsupportedOperationException("Unsupported primitive type.");
    }

    private Mesh createQuad() {
        Vector3[] vertices = {
                new Vector3(-1.0f, -1.0f, 0.0f),
                new Vector3(1.0f, -1.0f, 0.0f),
                new Vector3(-1.0f,  1.0f, 0.0f),
                new Vector3(-1.0f,  1.0f, 0.0f),
                new Vector3(1.0f, -1.0f, 0.0f),
                new Vector3(1.0f,  1.0f, 0.0f),
        };

        Vector3[] normals = {
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
        };

        Vector2[] uvCoordinates = {
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(0, 0),
                new Vector2(0, 0),
                new Vector2(1, 1),
                new Vector2(1, 0),
        };

        return new Mesh(vertices, normals, uvCoordinates);
    }

    private Mesh createCylinder() {
        return new CylinderFactory().createCylinder();
    }
}
