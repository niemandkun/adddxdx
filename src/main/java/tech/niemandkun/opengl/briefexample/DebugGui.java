package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Material;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.components.MeshSkin;
import tech.niemandkun.opengl.graphics.support.materials.DebugGuiMaterial;
import tech.niemandkun.opengl.math.Vector3;

public class DebugGui extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        Vector3[] vertices = {
                new Vector3(-1.0f, -1.0f, 0.0f),
                new Vector3(1.0f, -1.0f, 0.0f),
                new Vector3(-1.0f,  1.0f, 0.0f),
                new Vector3(-1.0f,  1.0f, 0.0f),
                new Vector3(1.0f, -1.0f, 0.0f),
                new Vector3(1.0f,  1.0f, 0.0f),
        };

        Mesh mesh = new Mesh(vertices);
        Material material = getScene().getMaterialFactory().get(DebugGuiMaterial.class);
        addComponent(new MeshSkin(mesh, material));
    }
}
