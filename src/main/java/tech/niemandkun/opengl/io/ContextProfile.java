package tech.niemandkun.opengl.io;

import static org.lwjgl.glfw.GLFW.*;

public enum ContextProfile {
    CORE(GLFW_OPENGL_CORE_PROFILE),
    ANY(GLFW_OPENGL_ANY_PROFILE),
    COMPAT(GLFW_OPENGL_COMPAT_PROFILE);

    private final int mProfile;

    ContextProfile(int profile) {
        mProfile = profile;
    }

    int getProfile() {
        return mProfile;
    }
}
