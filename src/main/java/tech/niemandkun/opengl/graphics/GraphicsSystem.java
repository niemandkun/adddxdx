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

    private final Set<Renderable> mRenderables;
    private Light mLight;
    private Camera mCamera;

    private final GlRenderer mRenderer;

    public GraphicsSystem(Window window, MaterialFactory materialFactory) {
        mRenderables = new HashSet<>();
        mRenderer = new GlRenderer(new GlWindowRenderTarget(window), materialFactory);
    }

    void setCamera(Camera camera) {
        mCamera = camera;
    }

    Camera getCamera() {
        return mCamera;
    }

    void setLight(Light light) {
        mLight = light;
    }

    Light getLight() {
        return mLight;
    }

    void addRenderable(Renderable renderable) {
        mRenderables.add(renderable);
    }

    void removeRenderable(Renderable renderable) {
        mRenderables.remove(renderable);
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
        mRenderer.renderAll(mCamera, mLight, mRenderables);
    }
}
