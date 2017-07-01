package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Size;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL14.*;

class GlDepthTexture extends GlTexture {
    @Override
    int getGlTextureFormat() {
        return GL_DEPTH_COMPONENT16;
    }

    GlDepthTexture(Size size) {
        super(size);
    }

    @Override
    int doInit(Size size) {
        int textureHandle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureHandle);
        glTexImage2D(GL_TEXTURE_2D, 0,  GL_DEPTH_COMPONENT16, size.getWidth(), size.getHeight(), 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, new float[] {1, 0, 0, 0});
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_FUNC, GL_LEQUAL);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_COMPARE_MODE, GL_COMPARE_R_TO_TEXTURE);

        return textureHandle;
    }
}
