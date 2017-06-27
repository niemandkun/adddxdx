package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector4;

import static org.lwjgl.opengl.GL11.*;

class GlWindowRenderTarget implements RenderTarget {
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
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
