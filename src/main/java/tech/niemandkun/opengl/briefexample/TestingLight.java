package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.support.components.DirectionalLight;

public class TestingLight extends Actor {
    @Override
    public void onCreate() {
        addComponent(new DirectionalLight());
        getTransform().rotate(0, -0.3f, 0);
        getTransform().rotate(0.4f, 0, 0);
    }
}
