package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.Size;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public abstract class GlTexture implements Destroyable {
    private static final int NULL_HANDLE = -1;
    private final Size mSize;
    private int mHandle;

    int getHandle() {
        return mHandle;
    }

    protected abstract int getGlTextureFormat();

    Size getSize() {
        return mSize;
    }

    protected GlTexture(Size size) {
        mHandle = NULL_HANDLE;
        mSize = size;
    }

    void init() {
        if (isInitialized())
            throw new UnsupportedOperationException("Trying to init texture, but it is already initialized.");

        mHandle = doInit(mSize);
    }

    protected abstract int doInit(Size size);

    int bind(int textureUnit) {
        if (!isInitialized())
            throw new UnsupportedOperationException("Trying to bind not initialized texture. Expected init() call.");

        glActiveTexture(GL_TEXTURE0 + textureUnit);
        glBindTexture(GL_TEXTURE_2D, mHandle);
        return textureUnit;
    }

    @Override
    public void destroy() {
        if (isInitialized())
            glDeleteTextures(mHandle);
    }

    boolean isInitialized() {
        return mHandle != NULL_HANDLE;
    }
}
