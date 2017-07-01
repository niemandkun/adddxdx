package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.components.MeshSkin;
import tech.niemandkun.opengl.graphics.support.materials.DefaultMaterial;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;

public class Triangles extends Actor {

    @Override
    public void onCreate() {
        super.onCreate();

        Vector3[] vertices = {
                new Vector3(-0.90f, -0.90f, 0),
                new Vector3(0.85f, -0.90f, 0),
                new Vector3(-0.90f, 0.85f, 0),
                new Vector3(0.90f, -0.85f, 0),
                new Vector3(0.90f, 0.90f, 0),
                new Vector3(-0.85f, 0.90f, 0),

                new Vector3(-0.90f, -0.90f, 0),
                new Vector3(-0.90f, 0.85f, 0),
                new Vector3(0.85f, -0.90f, 0),
                new Vector3(0.90f, -0.85f, 0),
                new Vector3(-0.85f, 0.90f, 0),
                new Vector3(0.90f, 0.90f, 0),
        };

        Vector3[] normals = {
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 1),

                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
                new Vector3(0, 0, -1),
        };

        DefaultMaterial material = getScene().getMaterialFactory().get(DefaultMaterial.class);
        material.setColor(Color.MATERIAL_DEEP_ORANGE);

        addComponent(new MeshSkin(new Mesh(vertices, normals), material));

        addComponent(new KeyboardController() {
            @Override
            public void checkKeyboardState(Keyboard keyboard) {
                if (keyboard.isPressed(Key.Q)) getTransform().rotate(0, -0.1f, 0);
                if (keyboard.isPressed(Key.E)) getTransform().rotate(0, 0.1f, 0);
            }
        });

        getTransform().translate(0, 1, -5);
    }
}
