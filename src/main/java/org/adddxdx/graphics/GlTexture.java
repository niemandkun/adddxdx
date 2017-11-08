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

public abstract class GlTexture implements Destroyable, Texture {
    private static final int NULL_HANDLE = -1;
    private Texture.WrapMode mWrapMode;
    private final Size mSize;
    private int mHandle;

    @Override
    public void setWrapMode(WrapMode wrapMode) {
        mWrapMode = wrapMode;
    }

    final int getHandle() {
        return mHandle;
    }

    public final Size getSize() {
        return mSize;
    }

    public final int getWidth() {
        return mSize.getWidth();
    }

    public final int getHeight() {
        return mSize.getHeight();
    }

    protected abstract int getGlTextureFormat();

    GlTexture(Size size, WrapMode wrapMode) {
        mHandle = NULL_HANDLE;
        mWrapMode = wrapMode;
        mSize = size;
    }

    @Override
    public final void init() {
        if (isInitialized()) {
            throw new UnsupportedOperationException("Trying to init texture, but it is already initialized.");
        }
        mHandle = glGenTextures();
        bindGlTexture();
        onInit();
    }

    protected void onInit() { }

    @Override
    public final int bind(int textureUnit) {
        if (!isInitialized()) {
            throw new UnsupportedOperationException("Trying to bind not initialized texture. Expected init() call.");
        }
        glActiveTexture(GL_TEXTURE0 + textureUnit);
        bindGlTexture();
        onBind();
        return textureUnit;
    }

    protected void onBind() { }

    private void bindGlTexture() {
        glBindTexture(GL_TEXTURE_2D, mHandle);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, mWrapMode.getGlWrapMode());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, mWrapMode.getGlWrapMode());
    }

    @Override
    public final void destroy() {
        if (isInitialized()) {
            glDeleteTextures(mHandle);
        }
    }

    @Override
    public final boolean isInitialized() {
        return mHandle != NULL_HANDLE;
    }
}
