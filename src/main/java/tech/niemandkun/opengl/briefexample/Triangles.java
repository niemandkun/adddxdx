package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.components.KeyboardController;
import tech.niemandkun.opengl.components.MeshRenderer;
import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Material;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.io.Key;
import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.math.*;

public class Triangles extends Actor {

    @Override
    public void onCreate() {
        Vector3[] vertices = new Vector3[]{
                new Vector3(-0.90f, -0.90f, 0),
                new Vector3(0.85f, -0.90f, 0),
                new Vector3(-0.90f, 0.85f, 0),
                new Vector3(0.90f, -0.85f, 0),
                new Vector3(0.90f, 0.90f, 0),
                new Vector3(-0.85f, 0.90f, 0),
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
        addComponent(new MeshRenderer(mesh, material));

        addComponent(new KeyboardController() {
            @Override
            protected void updateTransform(Transform actorTransform, Keyboard keyboard) {
                if (keyboard.isPressed(Key.J)) actorTransform.rotate(0, -0.1f, 0);
                if (keyboard.isPressed(Key.K)) actorTransform.rotate(0, 0.1f, 0);
            }
        });

        getTransform().translate(0, 0, -5);
    }
}
