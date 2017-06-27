package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.ActiveSystem;
import tech.niemandkun.opengl.io.Window;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class GraphicsSystem extends ActiveSystem<GraphicsSystem.Component> {
    public abstract static class Component extends tech.niemandkun.opengl.engine.Component {
        abstract void connect(GraphicsSystem system);
        abstract void disconnect(GraphicsSystem system);
    }

    private final Set<Renderer> mRenderers;
    private final RenderTarget mRenderTarget;
    private final Window mWindow;

    private Camera mCamera;

    public GraphicsSystem(Window window) {
        mRenderers = new HashSet<>();
        mRenderTarget = new GlWindowRenderTarget();

        mRenderTarget.init();
        mWindow = window;
    }

    void setCamera(Camera camera) {
        mCamera = camera;
        mCamera.adjustAspectRatio(mWindow.getSize());
    }

    Camera getCamera() {
        return mCamera;
    }

    void addRenderer(Renderer renderer) {
        mRenderers.add(renderer);
    }

    void removeRenderer(Renderer renderer) {
        mRenderers.remove(renderer);
    }

    @Override
    public void register(Component component) {
        component.connect(this);
    }

    @Override
    public void unregister(Component component) {
        component.disconnect(this);
    }

    @Override
    public void update(Duration timeSinceLastUpdate) {
        mRenderTarget.clear();

        if (mCamera == null) return;

        RenderSettings settings = new RenderSettings(mCamera.getMatrix());

        for (Renderer renderer : mRenderers)
            renderer.render(mRenderTarget, settings);
    }
}
