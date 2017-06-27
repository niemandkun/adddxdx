package tech.niemandkun.opengl.io;

public class WindowSettingsBuilder {

    private boolean mResizable = true;
    private boolean mDecorated = true;
    private boolean mFocused = true;
    private boolean mAutoIconify = true;
    private boolean mFloating = false;
    private boolean mMaximized = false;
    private boolean mFullscreen = false;

    private String title = "Window";

    private final PlatformBuilder mBuilder;

    WindowSettingsBuilder(PlatformBuilder builder) {
        mBuilder = builder;
    }

    public VideoModeBuilder onVideoMode() { return mBuilder.onVideoMode(); }
    public FramebufferSettingsBuilder onFramebuffer() { return mBuilder.onFramebuffer(); }
    public ContextSettingsBuilder onContext() { return mBuilder.onContext(); }
    public Platform build() { return mBuilder.build(); }

    String getTitle() {
        return title;
    }

    WindowSettings getSettings() {
        return new WindowSettings(mResizable, mDecorated, mFocused, mAutoIconify, mFloating, mMaximized, mFullscreen);
    }

    public WindowSettingsBuilder setResizable(boolean resizable) {
        mResizable = resizable;
        return this;
    }

    public WindowSettingsBuilder setDecorated(boolean decorated) {
        mDecorated = decorated;
        return this;
    }

    public WindowSettingsBuilder setFocused(boolean focused) {
        mFocused = focused;
        return this;
    }

    public WindowSettingsBuilder setAutoIconify(boolean autoIconify) {
        mAutoIconify = autoIconify;
        return this;
    }

    public WindowSettingsBuilder setFloating(boolean floating) {
        mFloating = floating;
        return this;
    }

    public WindowSettingsBuilder setMaximized(boolean maximized) {
        mMaximized = maximized;
        return this;
    }

    public WindowSettingsBuilder setFullscreen(boolean fullscreen) {
        mFullscreen = fullscreen;
        return this;
    }

    public WindowSettingsBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
}
