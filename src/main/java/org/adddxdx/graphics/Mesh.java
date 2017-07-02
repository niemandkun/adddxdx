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

import com.sun.istack.internal.NotNull;
import org.adddxdx.math.Vector2;
import org.adddxdx.math.Vector3;

public class Mesh {
    private final VertexArray mVertexArray;

    public VertexArray getVertexArray() { return mVertexArray; }

    public Mesh(@NotNull Vector3[] vertices) {
        mVertexArray = new VertexArray(vertices, null, null);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Vector3[] normals) {
        mVertexArray = new VertexArray(vertices, normals, null);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Vector2[] uvCoordinates) {
        mVertexArray = new VertexArray(vertices, null, uvCoordinates);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Vector3[] normals, @NotNull Vector2[] uvCoordinates) {
        mVertexArray = new VertexArray(vertices, normals, uvCoordinates);
    }
}
