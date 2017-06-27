package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.graphics.WindowRenderTarget;

class GlfwPlatformBuilder extends PlatformBuilder {
    @Override
    Platform buildPlatform(String title, VideoMode videoMode, WindowSettings windowSettings,
                         ContextSettings contextSettings, FramebufferSettings framebufferSettings) {

        GlfwWindow window = new GlfwWindow(title, videoMode, windowSettings, contextSettings, framebufferSettings);
        return new GlfwPlatform(new WindowRenderTarget(window), window.getKeyboard(), window, window.getMouse());
    }
}
