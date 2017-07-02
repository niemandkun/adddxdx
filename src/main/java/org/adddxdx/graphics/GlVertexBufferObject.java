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

import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

class GlVertexBufferObject {
    private final static int VERTEX_POSITION_ATTR_ID = 0;
    private final static int VERTEX_NORMAL_ATTR_ID = 1;
    private final static int VERTEX_UV_ATTR_ID = 2;
    private final static int SIZEOF_FLOAT = 4;

    private final static int NULL_HANDLE = -1;

    private final VertexArray mVertexArray;

    private int mObjectHandle;
    private int mBufferHandle;

    boolean isAllocated() {
        return mObjectHandle != NULL_HANDLE;
    }

    GlVertexBufferObject(VertexArray vertexArray) {
        mVertexArray = vertexArray;
        mObjectHandle = NULL_HANDLE;
        mBufferHandle = NULL_HANDLE;
    }

    void allocate() {
        if (mObjectHandle != NULL_HANDLE) throw new IllegalStateException("VBO is already allocated.");

        mObjectHandle = glGenVertexArrays();
        glBindVertexArray(mObjectHandle);

        mBufferHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mBufferHandle);
        glBufferData(GL_ARRAY_BUFFER, mVertexArray.getVertexBuffer(), GL_STATIC_DRAW);
    }

    void deallocate() {
        glDeleteVertexArrays(mObjectHandle);
        glDeleteBuffers(mBufferHandle);

        mObjectHandle = NULL_HANDLE;
        mBufferHandle = NULL_HANDLE;
    }

    void draw() {
        if (!isAllocated())
            throw new UnsupportedOperationException("Trying to render unallocated VBO. Expected allocate() call.");

        glBindVertexArray(mObjectHandle);
        glBindBuffer(GL_ARRAY_BUFFER, mBufferHandle);
        setupVertexAttributesPointers();

        glDrawArrays(GL_TRIANGLES, 0, mVertexArray.getVertexBuffer().length);
    }

    private void setupVertexAttributesPointers() {
        int stride = mVertexArray.getVertexSize() * SIZEOF_FLOAT;
        int offset = 0;

        glEnableVertexAttribArray(VERTEX_POSITION_ATTR_ID);
        GL20.glVertexAttribPointer(VERTEX_POSITION_ATTR_ID, VertexArray.POSITION_COMPONENTS_PER_VERTEX, GL_FLOAT, false, stride, offset);
        offset += VertexArray.POSITION_COMPONENTS_PER_VERTEX * SIZEOF_FLOAT;

        if (mVertexArray.hasNormalData()) {
            glEnableVertexAttribArray(VERTEX_NORMAL_ATTR_ID);
            GL20.glVertexAttribPointer(VERTEX_NORMAL_ATTR_ID, VertexArray.NORMAL_COMPONENTS_PER_VERTEX, GL_FLOAT, false, stride, offset);
            offset += VertexArray.NORMAL_COMPONENTS_PER_VERTEX * SIZEOF_FLOAT;
        }

        if (mVertexArray.hasUvData()) {
            glEnableVertexAttribArray(VERTEX_UV_ATTR_ID);
            GL20.glVertexAttribPointer(VERTEX_UV_ATTR_ID, VertexArray.UV_COMPONENTS_PER_VERTEX, GL_FLOAT, false, stride, offset);
        }
    }
}
