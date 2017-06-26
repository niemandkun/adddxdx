package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.Transform;

public abstract class KeyboardController extends InputSystem.Component implements KeyboardObserver {

    @Override
    public void checkKeyboard(Keyboard keyboard) {
        updateTransform(getActor().getTransform(), keyboard);
    }

    protected abstract void updateTransform(Transform actorTransform, Keyboard keyboard);
}
