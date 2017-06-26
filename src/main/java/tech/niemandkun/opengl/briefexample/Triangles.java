package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;
import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.graphics.*;

class Triangles implements Renderer, Destroyable {

    private final MeshRenderer mMeshRenderer;
    private final Mesh mMesh;

    Triangles(MaterialFactory materialFactory) {
        Vector3[] vertices = new Vector3[]{
                new Vector3(-0.90f, -0.90f, -10),
                new Vector3(0.85f, -0.90f, -5),
                new Vector3(-0.90f, 0.85f, -10),
                new Vector3(0.90f, -0.85f, -5),
                new Vector3(0.90f, 0.90f, -5),
                new Vector3(-0.85f, 0.90f, -10),
        };

        Color[] colors = new Color[]{
                new Color(255, 0, 0),
                new Color(0, 255, 0),
                new Color(0, 0, 255),
                new Color(0, 255, 0),
                new Color(255, 0, 0),
                new Color(0, 0, 255),
        };

        mMesh = new Mesh(vertices, colors);

        mMeshRenderer = new MeshRenderer(mMesh, materialFactory.getDefaultMaterial());
    }

    @Override
    public void render(RenderTarget target) {
        mMeshRenderer.render(target);
    }

    @Override
    public void destroy() {
        mMesh.destroy();
    }
}
