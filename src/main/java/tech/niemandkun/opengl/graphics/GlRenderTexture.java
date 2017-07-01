package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.*;

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
