package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.graphics.*;

public class MeshRenderer extends GraphicsSystem.Component implements Renderer {
    private final Mesh mMesh;
    private final Material mMaterial;

    public MeshRenderer(Mesh mesh, Material material) {
        mMesh = mesh;
        mMaterial = material;
    }

    @Override
    public void render(RenderTarget target) {
        target.render(mMesh.getVertexArray(), mMaterial, getActor().getTransform());
    }

    @Override
    public void onDestroy() {
        mMesh.destroy();
    }
}
