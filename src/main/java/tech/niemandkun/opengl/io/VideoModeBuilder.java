package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.math.Size;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class VideoModeBuilder {
    private Size mSize = new Size(800, 600);
    private int mRefreshRate = GLFW_DONT_CARE;

    private final WindowBuilder mBuilder;

    VideoModeBuilder(WindowBuilder builder) {
        mBuilder = builder;
    }

    public WindowSettingsBuilder onWindow() { return mBuilder.onWindow(); }
    public FramebufferSettingsBuilder onFramebuffer() { return mBuilder.onFramebuffer(); }
    public ContextSettingsBuilder onContext() { return mBuilder.onContext(); }
    public Window build() { return mBuilder.build(); }

    VideoMode getVideoMode() {
        return new VideoMode(mSize, mRefreshRate);
    }

    public VideoModeBuilder setSize(Size size) {
        mSize = size;
        return this;
    }

    public VideoModeBuilder setRefreshRate(int refreshRate) {
        mRefreshRate = refreshRate;
        return this;
    }
}
