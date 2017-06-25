package tech.niemandkun.opengl.io.builders;

import tech.niemandkun.opengl.io.*;

public class GlfwWindowBuilder extends WindowBuilder {
    @Override
    Window buildWindow(String title, VideoMode videoMode, WindowSettings windowSettings,
                       ContextSettings contextSettings, FramebufferSettings framebufferSettings) {
        return new GlfwWindow(title, videoMode, windowSettings, contextSettings, framebufferSettings);
    }
}
