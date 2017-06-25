package tech.niemandkun.opengl.io.builders;

import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_ANY_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_COMPAT_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;

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
