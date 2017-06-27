package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.graphics.*;

public class MeshRenderer extends GraphicsSystem.Component implements Renderer {
    private final Material mMaterial;
    private final VertexBufferObject mVertexBufferObject;

    public MeshRenderer(Mesh mesh, Material material) {
        mMaterial = material;
        mVertexBufferObject = new VertexBufferObject(mesh.getVertexArray());
    }

    @Override
    public void render(RenderTarget target) {
        target.render(mVertexBufferObject, mMaterial, getActor().getTransform());
    }

    @Override
    public void onDestroy() {
        if (mVertexBufferObject.isAllocated())
            mVertexBufferObject.deallocate();
    }

    @Override
    public void connect(GraphicsSystem system) {
        system.addRenderer(this);
    }

    @Override
    public void disconnect(GraphicsSystem system) {
        system.removeRenderer(this);
    }
}
