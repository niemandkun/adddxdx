package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Size;

import static org.lwjgl.opengl.GL11.*;

class GlRgbTexture extends GlTexture {
    @Override
    protected int getGlTextureFormat() {
        return GL_RGBA;
    }

    GlRgbTexture(Size size) {
        super(size);
    }

    @Override
    protected int doInit(Size size) {
        int textureHandle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureHandle);
        glTexImage2D(GL_TEXTURE_2D, 0,  GL_RGBA, size.getWidth(), size.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        return textureHandle;
    }
}
