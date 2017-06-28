package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.engine.ActiveSystem;

import java.time.Duration;

public class InputSystem extends ActiveSystem<InputSystem.Component> {
    public abstract static class Component extends tech.niemandkun.opengl.engine.Component {
        abstract void connect(Keyboard keyboard, Mouse mouse);
        abstract void disconnect(Keyboard keyboard, Mouse mouse);
    }

    private final GlfwKeyboard mKeyboard;
    private final GlfwMouse mMouse;

    InputSystem(GlfwKeyboard keyboard, GlfwMouse mouse) {
        mKeyboard = keyboard;
        mMouse = mouse;
    }

    @Override
    public void register(Component component) {
        component.connect(mKeyboard, mMouse);
    }

    @Override
    public void unregister(Component component) {
        component.disconnect(mKeyboard, mMouse);
    }

    @Override
    public void update(Duration timeSinceLastUpdate) {
        mKeyboard.update();
        mMouse.update();
    }
}
