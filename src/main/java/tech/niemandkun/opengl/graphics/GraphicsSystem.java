package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.ActiveSystem;

import java.util.HashSet;
import java.util.Set;

public class GraphicsSystem implements ActiveSystem<GraphicsSystem.Component> {
    public abstract static class Component extends tech.niemandkun.opengl.engine.Component {
        public abstract void connect(GraphicsSystem system);
        public abstract void disconnect(GraphicsSystem system);
    }

    private final Set<Renderer> mRenderers;
    private final RenderTarget mRenderTarget;

    public GraphicsSystem(RenderTarget renderTarget) {
        mRenderers = new HashSet<>();
        mRenderTarget = renderTarget;
    }

    public void addRenderer(Renderer renderer) {
        mRenderers.add(renderer);
    }

    public void removeRenderer(Renderer renderer) {
        mRenderers.remove(renderer);
    }

    public RenderTarget getCurrentRenderTarget() {
        return mRenderTarget;
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
    public void update() {
        mRenderTarget.clear();

        for (Renderer renderer : mRenderers)
            renderer.render(mRenderTarget);
    }
}
