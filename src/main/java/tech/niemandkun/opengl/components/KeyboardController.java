package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.io.*;

public abstract class KeyboardController extends InputSystem.Component implements KeyboardObserver {

    @Override
    public void checkKeyboardState(Keyboard keyboard) {
        onCheckKeyboardState(keyboard);
    }

    protected abstract void onCheckKeyboardState(Keyboard keyboard);
}
