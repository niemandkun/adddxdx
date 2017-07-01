package tech.niemandkun.opengl.graphics.support.components;

import tech.niemandkun.opengl.graphics.*;

public class MeshSkin extends GraphicsSystem.Component implements Renderable {
    private final Material mMaterial;
    private final Mesh mMesh;

    public MeshSkin(Mesh mesh) {
        mMaterial = null;
        mMesh = mesh;
    }

    public MeshSkin(Mesh mesh, Material material) {
        mMaterial = material;
        mMesh = mesh;
    }

    @Override
    public void render(Renderer renderer, RenderSettings settings) {
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
