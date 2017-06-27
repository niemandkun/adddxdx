package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Matrix4;

public class MeshRenderer extends GraphicsSystem.Component implements Renderer {
    private final Material mMaterial;
    private final GlVertexBufferObject mVertexBufferObject;

    public MeshRenderer(Mesh mesh, Material material) {
        mMaterial = material;
        mVertexBufferObject = new GlVertexBufferObject(mesh.getVertexArray());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!mVertexBufferObject.isAllocated())
            mVertexBufferObject.allocate();
    }

    @Override
    public void render(RenderTarget target, RenderSettings settings) {
        mMaterial.getShader().enable();
        Matrix4 modelMatrix = getActor().getTransform().getMatrix();
        Matrix4 mvp = settings.getViewProjectionMatrix().cross(modelMatrix);
        mMaterial.getShader().setUniform("mvp", mvp);
        mVertexBufferObject.render(target, settings);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVertexBufferObject.isAllocated())
            mVertexBufferObject.deallocate();
    }

    @Override
    void connect(GraphicsSystem system) {
        system.addRenderer(this);
    }

    @Override
    void disconnect(GraphicsSystem system) {
        system.removeRenderer(this);
    }
}
