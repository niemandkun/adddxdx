package tech.niemandkun.opengl.graphics.support.components;

import tech.niemandkun.opengl.graphics.*;

public class MeshSkin extends GraphicsSystem.Component implements Renderable {
    private final Material mMaterial;
    private final Mesh mMesh;

    private boolean mCastShadows;

    public MeshSkin(Mesh mesh) {
        this(mesh, null);
    }

    public MeshSkin(Mesh mesh, Material material) {
        mCastShadows = true;
        mMaterial = material;
        mMesh = mesh;
    }

    public boolean isCastingShadows() {
        return mCastShadows;
    }

    public void setCastShadows(boolean castShadows) {
        mCastShadows = castShadows;
    }

    @Override
    public void render(Renderer renderer, RenderSettings settings) {
        if (!mCastShadows && settings.isShadowPass()) return;

        renderer.render(mMesh.getVertexArray(), settings
                .putModelMatrix(getActor().getTransform().getMatrix())
                .putMaterial(mMaterial));
    }

    @Override
    protected void connect(GraphicsSystem system) {
        system.addRenderable(this);
    }

    @Override
    protected void disconnect(GraphicsSystem system) {
        system.removeRenderable(this);
    }
}
