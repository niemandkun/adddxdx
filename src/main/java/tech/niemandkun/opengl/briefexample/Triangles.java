package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.*;
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

        Color[] colors = {
                Color.MATERIAL_DEEP_ORANGE,
                Color.MATERIAL_GREEN,
                Color.MATERIAL_LIGHT_BLUE,
                Color.MATERIAL_GREEN,
                Color.MATERIAL_DEEP_ORANGE,
                Color.MATERIAL_LIGHT_BLUE,

                Color.MATERIAL_DEEP_ORANGE,
                Color.MATERIAL_LIGHT_BLUE,
                Color.MATERIAL_GREEN,
                Color.MATERIAL_GREEN,
                Color.MATERIAL_LIGHT_BLUE,
                Color.MATERIAL_DEEP_ORANGE,
        };

        Mesh mesh = new Mesh(vertices, normals, colors);
        Material material = getScene().getMaterialFactory().getDefaultMaterial();
        addComponent(new MeshRenderer(mesh, material));

        addComponent(new KeyboardController() {
            @Override
            public void checkKeyboardState(Keyboard keyboard) {
                if (keyboard.isPressed(Key.Q)) getTransform().rotate(0, 0.1f, 0);
                if (keyboard.isPressed(Key.E)) getTransform().rotate(0, -0.1f, 0);
            }
        });

        getTransform().translate(0, 1, -5);
    }
}
