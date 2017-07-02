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
import org.adddxdx.math.FMath;
import org.adddxdx.math.Vector3;

class CylinderFactory {
    private final static int RESOLUTION = 100;

    Mesh createCylinder() {
        int trianglesCount = 4 * RESOLUTION;
        float step = 2 * FMath.PI / RESOLUTION;

        Vector3[] vertices = new Vector3[trianglesCount * 3];
        Vector3[] normals = new Vector3[trianglesCount * 3];
        int offset = 0;

        for (int i = 0; i < RESOLUTION; ++i) {
            float phi1 = i * step;
            float phi2 = (i + 1) * step;
            float x1 = (float) Math.cos(phi1);
            float x2 = (float) Math.cos(phi2);
            float y1 = (float) Math.sin(phi1);
            float y2 = (float) Math.sin(phi2);

            vertices[offset]     = new Vector3(0, 0, 1);
            vertices[offset + 1] = new Vector3(x1, y1, 1);
            vertices[offset + 2] = new Vector3(x2, y2, 1);

            normals[offset]     = new Vector3(0, 0, 1);
            normals[offset + 1] = new Vector3(0, 0, 1);
            normals[offset + 2] = new Vector3(0, 0, 1);

            vertices[offset + 3] = new Vector3(x1, y1, -1);
            vertices[offset + 4] = new Vector3(0, 0, -1);
            vertices[offset + 5] = new Vector3(x2, y2, -1);

            normals[offset + 3] = new Vector3(0, 0, -1);
            normals[offset + 4] = new Vector3(0, 0, -1);
            normals[offset + 5] = new Vector3(0, 0, -1);

            vertices[offset + 6] = new Vector3(x1, y1, 1);
            vertices[offset + 7] = new Vector3(x1, y1, -1);
            vertices[offset + 8] = new Vector3(x2, y2, -1);

            normals[offset + 6] = new Vector3(x1, y1, 0);
            normals[offset + 7] = new Vector3(x1, y1, 0);
            normals[offset + 8] = new Vector3(x2, y2, 0);

            vertices[offset + 9]  = new Vector3(x2, y2, 1);
            vertices[offset + 10] = new Vector3(x1, y1, 1);
            vertices[offset + 11] = new Vector3(x2, y2, -1);

            normals[offset + 9]  = new Vector3(x2, y2, 0);
            normals[offset + 10] = new Vector3(x1, y1, 0);
            normals[offset + 11] = new Vector3(x2, y2, 0);

            offset += 12;
        }

        return new Mesh(vertices, normals);
    }
}
