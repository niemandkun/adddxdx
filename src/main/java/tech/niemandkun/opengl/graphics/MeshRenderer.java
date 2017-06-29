package tech.niemandkun.opengl.graphics;

public class MeshRenderer extends GraphicsSystem.Component implements Renderable {
    private final Material mMaterial;
    private final Mesh mMesh;

    public MeshRenderer(Mesh mesh) {
        mMaterial = null;
        mMesh = mesh;
    }

    public MeshRenderer(Mesh mesh, Material material) {
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
    void connect(GraphicsSystem system) {
        system.addRenderable(this);
    }

    @Override
    void disconnect(GraphicsSystem system) {
        system.removeRenderable(this);
    }
}
