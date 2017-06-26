package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.components.Camera;
import tech.niemandkun.opengl.components.KeyboardController;
import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.Transform;

public class Player extends Actor {
    @Override
    public void onCreate() {
        addComponent(new Camera());

        addComponent(new KeyboardController() {
            @Override
            protected void onKeyPressed(Transform actorTransform, Keyboard keyboard, KeyboardEvent event) {
                switch (event.getKey()) {
                    case Key.W: actorTransform.translate(0, 0, -1); break;
                    case Key.A: actorTransform.translate(-1, 0, 0); break;
                    case Key.S: actorTransform.translate(0, 0, 1); break;
                    case Key.D: actorTransform.translate(1, 0, 0); break;
                    case Key.Q: actorTransform.rotate(0, -0.1f, 0); break;
                    case Key.E: actorTransform.rotate(0, 0.1f, 0); break;
                }
            }
        });
    }
}
