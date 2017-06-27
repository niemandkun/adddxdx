package tech.niemandkun.opengl.io;

public class ContextSettingsBuilder {
    private int mMajorVersion = 3;
    private int mMinorVersion = 0;
    private ContextProfile mProfile = ContextProfile.ANY;

    private final PlatformBuilder mBuilder;

    ContextSettingsBuilder(PlatformBuilder builder) {
        mBuilder = builder;
    }

    public VideoModeBuilder onVideoMode() { return mBuilder.onVideoMode(); }
    public WindowSettingsBuilder onWindow() { return mBuilder.onWindow(); }
    public FramebufferSettingsBuilder onFramebuffer() { return mBuilder.onFramebuffer(); }
    public Platform build() { return mBuilder.build(); }

    ContextSettings getContextSettings() {
        return new ContextSettings(mMajorVersion, mMinorVersion, mProfile.getProfile());
    }

    public ContextSettingsBuilder setMajorVersion(int majorVersion) {
        mMajorVersion = majorVersion;
        return this;
    }

    public ContextSettingsBuilder setMinorVersion(int minorVersion) {
        mMinorVersion = minorVersion;
        return this;
    }

    public ContextSettingsBuilder setProfile(ContextProfile profile) {
        mProfile = profile;
        return this;
    }
}
