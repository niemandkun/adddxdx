package tech.niemandkun.opengl.io;

class GlfwPlatformBuilder extends PlatformBuilder {
    @Override
    Platform buildPlatform(String title, VideoMode videoMode, WindowSettings windowSettings,
                         ContextSettings contextSettings, FramebufferSettings framebufferSettings) {

        GlfwWindow window = new GlfwWindow(title, videoMode, windowSettings, contextSettings, framebufferSettings);
        return new GlfwPlatform(window);
    }
}
