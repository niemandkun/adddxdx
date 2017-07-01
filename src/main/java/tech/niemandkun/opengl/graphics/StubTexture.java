package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Size;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

class StubTexture extends GlTexture {
    private final static int WIDTH = 1, HEIGHT = 1;

    StubTexture() {
        super(new Size(WIDTH, HEIGHT));
    }

    @Override
    protected int getGlTextureFormat() {
        return 0;
    }

    @Override
    protected int doInit(Size size) {
        int handle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, handle);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        float data[] = { 1, 1, 1, 1 };

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, WIDTH, HEIGHT, 0, GL_RGBA, GL_FLOAT, data);

        return handle;
    }
}
