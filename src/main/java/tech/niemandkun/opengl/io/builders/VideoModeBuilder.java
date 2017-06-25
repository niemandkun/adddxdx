package tech.niemandkun.opengl.io.builders;

import tech.niemandkun.opengl.io.VideoMode;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class VideoModeBuilder {
    private int mWidth = 800;
    private int mHeight = 600;
    private int mRefreshRate = GLFW_DONT_CARE;

    private final WindowBuilder mBuilder;

    VideoModeBuilder(WindowBuilder builder) {
        mBuilder = builder;
    }

    public WindowBuilder and() {
        return mBuilder;
    }

    VideoMode getVideoMode() {
        return new VideoMode(mWidth, mHeight, mRefreshRate);
    }

    public VideoModeBuilder setWidth(int width) {
        mWidth = width;
        return this;
    }

    public VideoModeBuilder setHeight(int height) {
        mHeight = height;
        return this;
    }

    public VideoModeBuilder setRefreshRate(int refreshRate) {
        mRefreshRate = refreshRate;
        return this;
    }
}
