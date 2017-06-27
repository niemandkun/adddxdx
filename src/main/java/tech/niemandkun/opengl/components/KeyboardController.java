package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.io.*;

public abstract class KeyboardController extends InputSystem.Component implements Keyboard.Observer {

    @Override
    public void checkKeyboardState(Keyboard keyboard) {
        onCheckKeyboardState(keyboard);
    }

    @Override
    public void connect(Keyboard keyboard, Mouse mouse) {
        keyboard.addObserver(this);
    }

    @Override
    public void disconnect(Keyboard keyboard, Mouse mouse) {
        keyboard.removeObserver(this);
    }

    protected abstract void onCheckKeyboardState(Keyboard keyboard);
}
