package org.adddxdx.graphics;

import org.adddxdx.math.Size;

import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;

public interface Texture {
    int getWidth();

    int getHeight();

    Size getSize();

    void setWrapMode(WrapMode wrapMode);

    boolean isInitialized();

    void init();

    int bind(int textureUnitToUse);

    enum WrapMode {
        CLAMP_TO_EDGE(GL_CLAMP_TO_EDGE),
        CLAMP_TO_BORDER(GL_CLAMP_TO_BORDER),
        REPEAT(GL_REPEAT),
        MIRRORED_REPEAT(GL_MIRRORED_REPEAT);

        private int mGlWrapMode;

        WrapMode(int glWrapMode) {
            mGlWrapMode = glWrapMode;
        }

        int getGlWrapMode() {
            return mGlWrapMode;
        }
    }
}
