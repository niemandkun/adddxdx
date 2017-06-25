package tech.niemandkun.opengl.io.output;

class GlfwWindowBuilder extends WindowBuilder {
    @Override
    Window buildWindow(String title, VideoMode videoMode, WindowSettings windowSettings,
                       ContextSettings contextSettings, FramebufferSettings framebufferSettings) {
        return new GlfwWindow(title, videoMode, windowSettings, contextSettings, framebufferSettings);
    }
}
