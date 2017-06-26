package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.components.Camera;
import tech.niemandkun.opengl.engine.ActiveSystem;

import java.util.HashSet;
import java.util.Set;

public class GraphicsSystem implements ActiveSystem<GraphicsSystem.Component> {
    public abstract static class Component extends tech.niemandkun.opengl.engine.Component { }

    private final Set<Renderer> mRenderers;
    private final RenderTarget mRenderTarget;

    public GraphicsSystem(RenderTarget renderTarget) {
        mRenderers = new HashSet<>();
        mRenderTarget = renderTarget;
    }

    @Override
    public void register(Component component) {
        if (component instanceof Renderer)
            mRenderers.add(((Renderer) component));
        if (component instanceof Camera && mRenderTarget.getCamera() == null)
            mRenderTarget.setCamera(((Camera) component));
    }

    @Override
    public void unregister(Component component) {
        if (component instanceof Renderer)
            mRenderers.remove(component);
        if (component instanceof Camera && mRenderTarget.getCamera().equals(component))
            mRenderTarget.setCamera(null);
    }

    @Override
    public void update() {
        mRenderTarget.clear();

        for (Renderer renderer : mRenderers)
            renderer.render(mRenderTarget);
    }
}
