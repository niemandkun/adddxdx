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

import org.adddxdx.io.Window;
import org.adddxdx.math.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

class GlWindowRenderTarget implements RenderTarget {
    private final static int WINDOW_FRAMEBUFFER_HANDLE = 0;

    private final Window mWindow;

    GlWindowRenderTarget(Window window) {
        mWindow = window;
    }

    @Override
    public void init() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void enable() {
        glBindFramebuffer(GL_FRAMEBUFFER, WINDOW_FRAMEBUFFER_HANDLE);
        Size windowSize = mWindow.getSize();
        glViewport(0, 0, windowSize.getWidth(), windowSize.getHeight());
    }

    @Override
    public Size getSize() {
        return mWindow.getSize();
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void clear(Color color) {
        Vector4 clearColor = color.toVector4();
        glClearColor(clearColor.getX(), clearColor.getY(), clearColor.getZ(), clearColor.getW());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() { }
}
