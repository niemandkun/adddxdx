package tech.niemandkun.opengl.shaders;

import static org.lwjgl.opengl.GL20.glUseProgram;

public class GlProgram {
    private final int mHandle;

    GlProgram(int handle) {
        mHandle = handle;
    }

    public void enable() {
        glUseProgram(mHandle);
    }
}
