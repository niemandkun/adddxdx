package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.engine.ActiveSystem;

import java.util.HashSet;
import java.util.Set;

import static tech.niemandkun.opengl.io.Keyboard.KeyPressListener;
import static tech.niemandkun.opengl.io.Keyboard.KeyReleaseListener;

public class InputSystem implements ActiveSystem<InputSystem.Component> {
    public abstract static class Component extends tech.niemandkun.opengl.engine.Component { }

    private Set<KeyboardObserver> mKeyboardObservers;

    private GlfwKeyboard mKeyboard;

    public InputSystem(GlfwKeyboard keyboard) {
        mKeyboard = keyboard;
        mKeyboardObservers = new HashSet<>();
    }

    @Override
    public void register(Component component) {
        if (component instanceof KeyPressListener)
            mKeyboard.addKeyPressListener(((KeyPressListener) component));
        if (component instanceof KeyReleaseListener)
            mKeyboard.addKeyReleaseListener(((KeyReleaseListener) component));
        if (component instanceof KeyboardObserver)
            mKeyboardObservers.add((KeyboardObserver) component);
    }

    @Override
    public void unregister(Component component) {
        if (component instanceof KeyPressListener)
            mKeyboard.removeKeyPressListener(((KeyPressListener) component));
        if (component instanceof KeyReleaseListener)
            mKeyboard.removeKeyReleasedListener(((KeyReleaseListener) component));
        if (component instanceof KeyboardObserver)
            mKeyboardObservers.remove(component);
    }

    @Override
    public void update() {
        mKeyboard.deliverEvents();
        for (KeyboardObserver observer : mKeyboardObservers)
            observer.checkKeyboardState(mKeyboard);
    }
}
