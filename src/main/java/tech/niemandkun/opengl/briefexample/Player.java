package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.support.components.PerspectiveCamera;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.Transform;
import tech.niemandkun.opengl.math.Vector3;

import static tech.niemandkun.opengl.math.FMath.PI;
import static tech.niemandkun.opengl.math.FMath.clamp;

public class Player extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();
        addComponent(new PerspectiveCamera());

        final Transform transform = getTransform();
        transform.translate(0, 1, 0);

        addComponent(new KeyboardController() {
            @Override public void checkKeyboardState(Keyboard keyboard) {
                Vector3 forward = transform.getViewDirection().setY(0).normalize().div(8);
                Vector3 backward = forward.negate();
                Vector3 left = forward.rotateAroundOy(PI / 2);
                Vector3 right = left.negate();

                if (keyboard.isPressed(Key.W)) transform.translate(forward);
                if (keyboard.isPressed(Key.S)) transform.translate(backward);
                if (keyboard.isPressed(Key.A)) transform.translate(left);
                if (keyboard.isPressed(Key.D)) transform.translate(right);
            }
        });

        addComponent(new MouseController() {
            float rotationY;
            float rotationX;
            float maxRotationX = PI / 2 - 0.001f;

            @Override public void onPointerMoved(Mouse mouse, MouseEvent event) {
                rotationX = clamp(rotationX + event.getPointerMovement().getY() / 200, -maxRotationX, maxRotationX);
                rotationY -= event.getPointerMovement().getX() / 200;

                transform.setRotation(Vector3.ZERO);
                transform.rotate(0, rotationY, 0);
                transform.rotate(rotationX, 0, 0);

                System.out.println(transform.getRotation());
            }
        });
    }
}
