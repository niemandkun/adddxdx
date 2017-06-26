package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.components.Camera;
import tech.niemandkun.opengl.components.KeyboardController;
import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.io.Key;
import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.math.Transform;

public class Player extends Actor {
    @Override
    public void onCreate() {
        addComponent(new Camera());

        addComponent(new KeyboardController() {
            @Override
            protected void updateTransform(Transform actorTransform, Keyboard keyboard) {
                if (keyboard.isPressed(Key.W)) actorTransform.translate(0, 0, -.5f);
                if (keyboard.isPressed(Key.A)) actorTransform.translate(-.5f, 0, 0);
                if (keyboard.isPressed(Key.S)) actorTransform.translate(0, 0, .5f);
                if (keyboard.isPressed(Key.D)) actorTransform.translate(.5f, 0, 0);
                if (keyboard.isPressed(Key.Q)) actorTransform.rotate(0, -0.02f, 0);
                if (keyboard.isPressed(Key.E)) actorTransform.rotate(0, 0.02f, 0);
            }
        });
    }
}
