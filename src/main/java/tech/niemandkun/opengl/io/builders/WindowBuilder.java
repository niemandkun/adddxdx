package tech.niemandkun.opengl.io.builders;

import tech.niemandkun.opengl.io.*;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class WindowBuilder {
    private static final int DEFAULT_REFRESH_RATE = GLFW_DONT_CARE;
    private static final int DEFAULT_BITS_PER_PIXEL = 32;

    private VideoModeBuilder mVideoMode;
    private WindowSettingsBuilder mWindowSettings;
    private FramebufferSettingsBuilder mFramebufferSettings;
    private ContextSettingsBuilder mContextSettings;

    public WindowBuilder() {

    }

    public VideoModeBuilder onVideoMode() { return mVideoMode; }
    public WindowSettingsBuilder onWindow() { return mWindowSettings; }
    public FramebufferSettingsBuilder onFramebuffer() { return mFramebufferSettings; }
    public ContextSettingsBuilder onContext() { return mContextSettings; }

    public Window build() {
        return new GlfwWindow()
    }
}
