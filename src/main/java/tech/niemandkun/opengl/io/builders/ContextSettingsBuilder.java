package tech.niemandkun.opengl.io.builders;

import tech.niemandkun.opengl.io.ContextSettings;

public class ContextSettingsBuilder {
    private int mMajorVersion = 3;
    private int mMinorVersion = 0;
    private ContextProfile mProfile = ContextProfile.ANY;

    private final WindowBuilder mBuilder;

    ContextSettingsBuilder(WindowBuilder builder) {
        mBuilder = builder;
    }

    public WindowBuilder and() {
        return mBuilder;
    }

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
