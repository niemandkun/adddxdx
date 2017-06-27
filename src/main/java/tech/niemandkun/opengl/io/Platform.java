package tech.niemandkun.opengl.io;

public interface Platform {
    InputSystem getInputSystem();
    Keyboard getKeyboard();
    Window getWindow();
    Mouse getMouse();

    static PlatformBuilder builder() {
        return new GlfwPlatformBuilder();
    }
}
