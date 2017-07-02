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

import org.adddxdx.engine.Destroyable;
import org.adddxdx.math.Size;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public abstract class GlTexture implements Destroyable {
    private static final int NULL_HANDLE = -1;
    private final Size mSize;
    private int mHandle;

    int getHandle() {
        return mHandle;
    }

    protected abstract int getGlTextureFormat();

    Size getSize() {
        return mSize;
    }

    protected GlTexture(Size size) {
        mHandle = NULL_HANDLE;
        mSize = size;
    }

    void init() {
        if (isInitialized())
            throw new UnsupportedOperationException("Trying to init texture, but it is already initialized.");

        mHandle = doInit(mSize);
    }

    protected abstract int doInit(Size size);

    int bind(int textureUnit) {
        if (!isInitialized())
            throw new UnsupportedOperationException("Trying to bind not initialized texture. Expected init() call.");

        glActiveTexture(GL_TEXTURE0 + textureUnit);
        glBindTexture(GL_TEXTURE_2D, mHandle);
        return textureUnit;
    }

    @Override
    public void destroy() {
        if (isInitialized())
            glDeleteTextures(mHandle);
    }

    boolean isInitialized() {
        return mHandle != NULL_HANDLE;
    }
}
