package tech.niemandkun.opengl.shaders;

import static org.lwjgl.opengl.GL20.*;

class GlShader {
    private final int mHandle;

    GlShader(int handle) {
        mHandle = handle;
    }

    void unload() {
        glDeleteShader(mHandle);
    }

    void attachTo(int programHandle) {
        glAttachShader(programHandle, mHandle);
    }

    void detachFrom(int programHandle) {
        glDetachShader(programHandle, mHandle);
    }
}
