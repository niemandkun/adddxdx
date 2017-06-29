package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.io.Window;
import tech.niemandkun.opengl.math.*;

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
