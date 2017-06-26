package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.infrastructure.Destroyable;

import static org.lwjgl.opengl.GL20.*;

class GlShader implements Destroyable {
    private final int mHandle;

    GlShader(int handle) {
        mHandle = handle;
    }

    @Override
    public void destroy() {
        glDeleteShader(mHandle);
    }

    void attachTo(int programHandle) {
        glAttachShader(programHandle, mHandle);
    }

    void detachFrom(int programHandle) {
        glDetachShader(programHandle, mHandle);
    }
}
