package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Camera;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.Transform;
import tech.niemandkun.opengl.math.Vector2;

public class Player extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        getTransform().translate(0, 1, 0);

        addComponent(new Camera());

        addComponent(new KeyboardController() {

            @Override
            protected void onCheckKeyboardState(Keyboard keyboard) {
                Transform transform = getTransform();

                Vector2 forward = Vector2.ORT_X.rotate(transform.getRotation().getY()).div(8);
                Vector2 backward = forward.negate();
                Vector2 left = forward.getNormal();
                Vector2 right = left.negate();

                if (keyboard.isPressed(Key.W)) transform.translate(forward.getY(), 0, forward.getX());
                if (keyboard.isPressed(Key.S)) transform.translate(backward.getY(), 0, backward.getX());
                if (keyboard.isPressed(Key.A)) transform.translate(left.getY(), 0, left.getX());
                if (keyboard.isPressed(Key.D)) transform.translate(right.getY(), 0, right.getX());
                if (keyboard.isPressed(Key.Q)) transform.rotate(0, -0.04f, 0);
                if (keyboard.isPressed(Key.E)) transform.rotate(0, 0.04f, 0);
            }
        });
    }
}
