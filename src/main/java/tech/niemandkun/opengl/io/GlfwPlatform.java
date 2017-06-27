package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.graphics.RenderTarget;
import tech.niemandkun.opengl.graphics.WindowRenderTarget;

class GlfwPlatform implements Platform {
    private final WindowRenderTarget mRenderTarget;
    private final GlfwKeyboard mKeyboard;
    private final GlfwWindow mWindow;
    private final GlfwMouse mMouse;

    GlfwPlatform(WindowRenderTarget renderTarget, GlfwKeyboard keyboard, GlfwWindow window, GlfwMouse mouse) {
        mRenderTarget = renderTarget;
        mKeyboard = keyboard;
        mWindow = window;
        mMouse = mouse;
    }

    @Override
    public RenderTarget getRenderTarget() {
        return mRenderTarget;
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
