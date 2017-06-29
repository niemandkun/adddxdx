package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.support.PerspectiveCamera;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.*;

public class Player extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();
        addComponent(new PerspectiveCamera());

        final Transform transform = getTransform();
        transform.translate(0, 1, 0);

        addComponent(new KeyboardController() {
            @Override public void checkKeyboardState(Keyboard keyboard) {
                Vector2 forward = Vector2.ORT_X.rotate(transform.getRotation().getY()).div(8);
                Vector2 backward = forward.negate();
                Vector2 left = forward.getNormal();
                Vector2 right = left.negate();

                if (keyboard.isPressed(Key.W)) transform.translate(forward.getY(), 0, forward.getX());
                if (keyboard.isPressed(Key.S)) transform.translate(backward.getY(), 0, backward.getX());
                if (keyboard.isPressed(Key.A)) transform.translate(left.getY(), 0, left.getX());
                if (keyboard.isPressed(Key.D)) transform.translate(right.getY(), 0, right.getX());
            }
        });

        addComponent(new MouseController() {
            float rotationY;
            float rotationX;
            float maxRotationX = (float) Math.PI / 2;

            @Override public void onPointerMoved(Mouse mouse, MouseEvent event) {
                rotationX = clamp(rotationX - event.getPointerMovement().getY() / 200, -maxRotationX, maxRotationX);
                rotationY += event.getPointerMovement().getX() / 200;

                transform.setRotation(Vector3.ZERO);
                transform.rotate(0, rotationY, 0);
                transform.rotate(rotationX, 0, 0);
            }

            private float clamp(float source, float min, float max) {
                if (source < min) return min;
                if (source > max) return max;
                return source;
            }
        });
    }
}
