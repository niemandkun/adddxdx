package tech.niemandkun.opengl.io;

public abstract class KeyboardController extends InputSystem.Component implements Keyboard.Observer {

    @Override
    public void checkKeyboardState(Keyboard keyboard) {
        onCheckKeyboardState(keyboard);
    }

    @Override
    void connect(Keyboard keyboard, Mouse mouse) {
        keyboard.addObserver(this);
    }

    @Override
    void disconnect(Keyboard keyboard, Mouse mouse) {
        keyboard.removeObserver(this);
    }

    protected abstract void onCheckKeyboardState(Keyboard keyboard);
}
