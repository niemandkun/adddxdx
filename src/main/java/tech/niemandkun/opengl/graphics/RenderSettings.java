package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Matrix4;

class RenderSettings {
    private final Matrix4 mViewProjectionMatrix;
    private final Matrix4 mLightMatrix;
    private final Matrix4 mViewMatrix;

    RenderSettings(Matrix4 viewProjectionMatrix) {
        this(viewProjectionMatrix, null, null);
    }

    RenderSettings(Matrix4 viewProjectionMatrix, Matrix4 viewMatrix, Matrix4 lightMatrix) {
        mViewProjectionMatrix = viewProjectionMatrix;
        mLightMatrix = lightMatrix;
        mViewMatrix = viewMatrix;
    }

    Matrix4 getViewProjectionMatrix() {
        return mViewProjectionMatrix;
    }

    Matrix4 getLightMatrix() {
        return mLightMatrix;
    }

    Matrix4 getViewMatrix() {
        return mViewMatrix;
    }
}
