package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Matrix4;

class RenderSettings {
    static final RenderSettings DEFAULT = new RenderSettings(Matrix4.IDENTITY);

    private final Matrix4 mViewProjectionMatrix;

    RenderSettings(Matrix4 viewProjectionMatrix) {
        mViewProjectionMatrix = viewProjectionMatrix;
    }

    Matrix4 getViewProjectionMatrix() {
        return mViewProjectionMatrix;
    }
}
