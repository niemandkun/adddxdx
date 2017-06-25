package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.io.builders.ContextProfile;

public class ContextSettings {
    private final int mMajorVersion;
    private final int mMinorVersion;
    private final int mProfile;

    public ContextSettings(int majorVersion, int minorVersion, int profile) {
        mMajorVersion = majorVersion;
        mMinorVersion = minorVersion;
        mProfile = profile;
    }

    int getMajorVersion() {
        return mMajorVersion;
    }

    int getMinorVersion() {
        return mMinorVersion;
    }

    int getProfile() {
        return mProfile;
    }
}
