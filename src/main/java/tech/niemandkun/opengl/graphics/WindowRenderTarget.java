package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.components.Camera;
import tech.niemandkun.opengl.io.Window;
import tech.niemandkun.opengl.math.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class WindowRenderTarget implements RenderTarget {
    private final Window mWindow;
    private Camera mCamera;

    public WindowRenderTarget(Window window) {
        mWindow = window;
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
        mCamera.adjustAspectRatio(mWindow.getSize());
    }

    public Camera getCamera() {
        return mCamera;
    }

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
    public void render(VertexBufferObject vertices, Material material, Transform transform) {
        if (mCamera == null)
            return;

        if (!vertices.isAllocated()) vertices.allocate();

        glUseProgram(material.getShader().getHandle());

        Matrix4 projectionViewMatrix = getCamera().getMatrix();
        Matrix4 modelMatrix = transform.getMatrix();
        material.getShader().setUniform("mvp", projectionViewMatrix.cross(modelMatrix));

        vertices.drawArray();
    }

    @Override
    public void destroy() { }
}
