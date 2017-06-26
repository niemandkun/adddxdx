package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.components.MeshRenderer;
import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;

public class Triangles extends Actor implements Renderer {

    private MeshRenderer mMeshRenderer;

    @Override
    public void onCreate() {
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

        Mesh mesh = new Mesh(vertices, colors);
        Material material = getScene().getMaterialFactory().getDefaultMaterial();
        addComponent(mMeshRenderer = new MeshRenderer(mesh, material));
    }

    @Override
    public void render(RenderTarget target) {
        mMeshRenderer.render(target);
    }
}
