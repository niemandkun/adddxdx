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

import org.adddxdx.math.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

class GlRenderTexture implements RenderTarget {
    private int mFramebufferHandle;

    private final GlTexture mTexture;

    GlTexture getTexture() {
        return mTexture;
    }

    GlRenderTexture(GlTexture texture) {
        mTexture = texture;
    }

    @Override
    public void init() {
        mTexture.init();
        int textureAttachmentType = getTextureAttachmentType();

        mFramebufferHandle = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, mFramebufferHandle);
        glFramebufferTexture(GL_FRAMEBUFFER, textureAttachmentType, mTexture.getHandle(), 0);

        if (textureAttachmentType != GL_DEPTH_ATTACHMENT) {
            int depthRenderBuffer = glGenRenderbuffers();
            glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBuffer);
            glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, getSize().getWidth(), getSize().getHeight());
            glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBuffer);
        }

        glDrawBuffer(textureAttachmentType == GL_DEPTH_ATTACHMENT ? GL_NONE : textureAttachmentType);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            throw new IllegalStateException("Error initializing framebuffer for render texture.");
    }

    private int getTextureAttachmentType() {
        return mTexture.getGlTextureFormat() == GL_DEPTH_COMPONENT16
                ? GL_DEPTH_ATTACHMENT
                : GL_COLOR_ATTACHMENT0;
    }

    @Override
    public void enable() {
        glBindFramebuffer(GL_FRAMEBUFFER, mFramebufferHandle);
        Size textureSize = mTexture.getSize();
        glViewport(0, 0, textureSize.getWidth(), textureSize.getHeight());
    }

    @Override
    public Size getSize() {
        return mTexture.getSize();
    }

    @Override
    public void clear(Color color) {
        Vector4 clearColor = color.toVector4();
        glClearColor(clearColor.getX(), clearColor.getY(), clearColor.getZ(), clearColor.getW());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void destroy() {
        glDeleteFramebuffers(mFramebufferHandle);
        mTexture.destroy();
    }
}
