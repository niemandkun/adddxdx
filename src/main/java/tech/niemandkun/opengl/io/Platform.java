package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.graphics.RenderTarget;

public interface Platform {
    RenderTarget getRenderTarget();
    InputSystem getInputSystem();
    Keyboard getKeyboard();
    Window getWindow();
    Mouse getMouse();

    static PlatformBuilder builder() {
        return new GlfwPlatformBuilder();
    }
}
