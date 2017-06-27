package tech.niemandkun.opengl.io;

class GlfwPlatform implements Platform {
    private final InputSystem mInputSystem;
    private final GlfwKeyboard mKeyboard;
    private final GlfwWindow mWindow;
    private final GlfwMouse mMouse;

    GlfwPlatform(GlfwWindow window) {
        mWindow = window;

        mKeyboard = window.getKeyboard();
        mMouse = window.getMouse();

        mInputSystem = new InputSystem(mKeyboard, mMouse);
    }

    @Override
    public InputSystem getInputSystem() {
        return mInputSystem;
    }

    @Override
    public Keyboard getKeyboard() {
        return mKeyboard;
    }

    @Override
    public Window getWindow() {
        return mWindow;
    }

    @Override
    public Mouse getMouse() {
        return mMouse;
    }
}
