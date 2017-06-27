package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

class GlRenderTexture implements RenderTarget {
    private int mFramebufferHandle;
    private int mTextureHandle;

    int getTextureHandle() {
        return mTextureHandle;
    }

    private final int mWidth;
    private final int mHeight;

    GlRenderTexture(Size size) {
        mWidth = size.getWidth();
        mHeight = size.getHeight();
    }

    @Override
    public void init() {
        mFramebufferHandle = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, mFramebufferHandle);

        mTextureHandle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, mTextureHandle);
        glTexImage2D(GL_TEXTURE_2D, 0,  GL_DEPTH_COMPONENT16, mWidth, mHeight, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);

        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, mTextureHandle, 0);

        glDrawBuffer(GL_NONE);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            throw new IllegalStateException("Error initializing framebuffer for render texture.");
    }

    @Override
    public void enable() {
        glBindFramebuffer(GL_FRAMEBUFFER, mFramebufferHandle);
        glViewport(0, 0, mWidth, mHeight);
    }

    int bind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mTextureHandle);
        return 0;
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
        glDeleteTextures(mTextureHandle);
    }
}
