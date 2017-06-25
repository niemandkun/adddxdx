package tech.niemandkun.opengl.io;

import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_ANY_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_COMPAT_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;

public class ContextSettings {
    public static final int PROFILE_CORE = GLFW_OPENGL_CORE_PROFILE;
    public static final int PROFILE_ANY = GLFW_OPENGL_ANY_PROFILE;
    public static final int PROFILE_COMPAT = GLFW_OPENGL_COMPAT_PROFILE;

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
