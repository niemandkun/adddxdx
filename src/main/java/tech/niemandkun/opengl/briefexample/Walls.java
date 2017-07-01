package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.components.MeshSkin;
import tech.niemandkun.opengl.graphics.support.materials.DefaultMaterial;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;

public class Walls extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        Vector3[] vertices = {

                // floor
                new Vector3(-100, 0, 100),
                new Vector3(100, 0, 100),
                new Vector3(100, 0, -100),
                new Vector3(-100, 0, 100),
                new Vector3(100, 0, -100),
                new Vector3(-100, 0, -100),

                // wall
                new Vector3(-10, 10, 10),
                new Vector3(10, 10, 10),
                new Vector3(10, 0, 10),
                new Vector3(-10, 10, 10),
                new Vector3(10, 0, 10),
                new Vector3(-10, 0, 10),

                // wall (back)
                new Vector3(-10, 10, 10.5f),
                new Vector3(10, 0, 10.5f),
                new Vector3(10, 10, 10.5f),
                new Vector3(-10, 10, 10.5f),
                new Vector3(-10, 0, 10.5f),
                new Vector3(10, 0, 10.5f),

                // wall (side)
                new Vector3(10, 0, 10.5f),
                new Vector3(10, 10, 10),
                new Vector3(10, 10, 10.5f),
                new Vector3(10, 0, 10.5f),
                new Vector3(10, 0, 10),
                new Vector3(10, 10, 10),
        };

        Vector3[] normals = {
                // floor
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),
                new Vector3(0, 1, 0),

                // wall
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),

                // wall (back)
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),

                // wall (side)
                new Vector3(1, 0, 0),
                new Vector3(1, 0, 0),
                new Vector3(1, 0, 0),
                new Vector3(1, 0, 0),
                new Vector3(1, 0, 0),
                new Vector3(1, 0, 0),
        };

        Color wallColor = new Color(0xDCDCAAFF);

        DefaultMaterial material = getScene().getMaterialFactory().get(DefaultMaterial.class);
        material.setColor(wallColor);

        addComponent(new MeshSkin(new Mesh(vertices, normals), material));
    }
}
