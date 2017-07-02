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
import com.sun.istack.internal.Nullable;
import org.adddxdx.math.Vector2;
import org.adddxdx.math.Vector3;

class VertexArray {
    final static int POSITION_COMPONENTS_PER_VERTEX = 3;
    final static int NORMAL_COMPONENTS_PER_VERTEX = 3;
    final static int UV_COMPONENTS_PER_VERTEX = 2;

    private final float[] mVertexBuffer;

    private final boolean mHasNormalData;
    private final boolean mHasUvData;

    private final int mVertexSize;

    float[] getVertexBuffer() { return mVertexBuffer; }

    boolean hasNormalData() { return mHasNormalData; }
    boolean hasUvData() { return mHasUvData; }

    int getVertexSize() { return mVertexSize; }

    VertexArray(@NotNull Vector3[] vertices, @Nullable Vector3[] normals, @Nullable Vector2[] uvCoordinates) {

        if (vertices == null) throw new IllegalArgumentException("Expected vertices data, but got null.");

        if (!validateArraysLength(vertices, normals, uvCoordinates))
            throw new IllegalArgumentException("Length of all arrays data should match.");

        mHasNormalData = normals != null;
        mHasUvData = uvCoordinates != null;

        mVertexBuffer = packToFloatArray(vertices, normals, uvCoordinates);
        mVertexSize = mVertexBuffer.length / vertices.length;
    }

    private float[] packToFloatArray(Vector3[] vertices, Vector3[] normals, Vector2[] uvCoordinates) {
        float[] vertexBuffer = new float[calculateTotalLength(vertices, normals, uvCoordinates)];
        float[] buffer = new float[POSITION_COMPONENTS_PER_VERTEX];

        int positionArrayOffset = 0;

        for (int i = 0; i < vertices.length; ++i) {
            vertices[i].toFloatArray(buffer);
            for (int j = 0; j < POSITION_COMPONENTS_PER_VERTEX; ++j)
                vertexBuffer[positionArrayOffset++] = buffer[j];

            if (normals != null) {
                normals[i].toFloatArray(buffer);
                for (int j = 0; j < NORMAL_COMPONENTS_PER_VERTEX; ++j)
                    vertexBuffer[positionArrayOffset++] = buffer[j];
            }

            if (uvCoordinates != null) {
                uvCoordinates[i].toFloatArray(buffer);
                for (int j = 0; j < UV_COMPONENTS_PER_VERTEX; ++j)
                    vertexBuffer[positionArrayOffset++] = buffer[j];
            }
        }

        return vertexBuffer;
    }

    private static boolean validateArraysLength(@NotNull Object[] first, Object[]... others) {
        boolean isValid = true;

        for (Object[] array : others)
            isValid &= !(array != null && first.length != array.length);

        return isValid;
    }

    private static int calculateTotalLength(Vector3[] vertices, Vector3[] normals, Vector2[] uvCoordinates) {
        int totalLength = 0;

        if (vertices != null) totalLength += vertices.length * POSITION_COMPONENTS_PER_VERTEX;
        if (normals != null) totalLength += normals.length * NORMAL_COMPONENTS_PER_VERTEX;
        if (uvCoordinates != null) totalLength += uvCoordinates.length * UV_COMPONENTS_PER_VERTEX;

        return totalLength;
    }
}
