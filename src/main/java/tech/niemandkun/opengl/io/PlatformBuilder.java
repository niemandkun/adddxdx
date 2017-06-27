package tech.niemandkun.opengl.io;

public abstract class PlatformBuilder {
    private VideoModeBuilder mVideoMode = new VideoModeBuilder(this);
    private WindowSettingsBuilder mWindowSettings = new WindowSettingsBuilder(this);
    private FramebufferSettingsBuilder mFramebufferSettings = new FramebufferSettingsBuilder(this);
    private ContextSettingsBuilder mContextSettings = new ContextSettingsBuilder(this);

    public VideoModeBuilder onVideoMode() { return mVideoMode; }
    public WindowSettingsBuilder onWindow() { return mWindowSettings; }
    public FramebufferSettingsBuilder onFramebuffer() { return mFramebufferSettings; }
    public ContextSettingsBuilder onContext() { return mContextSettings; }

    public Platform build() {
        return buildPlatform(
                mWindowSettings.getTitle(),
                mVideoMode.getVideoMode(),
                mWindowSettings.getSettings(),
                mContextSettings.getContextSettings(),
                mFramebufferSettings.getFramebufferSettings()
        );
    }

    abstract Platform buildPlatform(String title, VideoMode videoMode,
                                    WindowSettings windowSettings,
                                    ContextSettings contextSettings,
                                    FramebufferSettings framebufferSettings);
}
