package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.graphics.RenderTarget;
import tech.niemandkun.opengl.graphics.WindowRenderTarget;

class GlfwPlatform implements Platform {
    private final WindowRenderTarget mRenderTarget;
    private final InputSystem mInputSystem;
    private final GlfwKeyboard mKeyboard;
    private final GlfwWindow mWindow;
    private final GlfwMouse mMouse;

    GlfwPlatform(WindowRenderTarget renderTarget, GlfwWindow window) {
        mRenderTarget = renderTarget;
        mWindow = window;

        mKeyboard = window.getKeyboard();
        mMouse = window.getMouse();

        mInputSystem = new InputSystem(mKeyboard, mMouse);
    }

    @Override
    public RenderTarget getRenderTarget() {
        return mRenderTarget;
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
