package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.engine.ActiveSystem;

public class InputSystem implements ActiveSystem<InputSystem.Component> {
    public abstract static class Component extends tech.niemandkun.opengl.engine.Component {
        public abstract void connect(Keyboard keyboard, Mouse mouse);
        public abstract void disconnect(Keyboard keyboard, Mouse mouse);
    }

    private final GlfwKeyboard mKeyboard;
    private final GlfwMouse mMouse;

    public InputSystem(GlfwKeyboard keyboard, GlfwMouse mouse) {
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
    public void update() {
        mKeyboard.update();
    }
}
