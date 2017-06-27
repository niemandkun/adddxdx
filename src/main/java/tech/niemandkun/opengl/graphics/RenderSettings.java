package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Matrix4;

class RenderSettings {
    static final RenderSettings DEFAULT = new RenderSettings(Matrix4.IDENTITY, null);

    private final Matrix4 mViewProjectionMatrix;
    private final Matrix4 mLightMatrix;
//    private final Vector3 mLightDirection;
//
    RenderSettings(Matrix4 viewProjectionMatrix) {
        this(viewProjectionMatrix, null);
    }

    RenderSettings(Matrix4 viewProjectionMatrix, Matrix4 lightMatrix) {
        mViewProjectionMatrix = viewProjectionMatrix;
        mLightMatrix = lightMatrix;
//        mLightDirection = lightDirection;
    }

    Matrix4 getViewProjectionMatrix() {
        return mViewProjectionMatrix;
    }

    Matrix4 getLightMatrix() {
        return mLightMatrix;
    }
//
//    Vector3 getLightDirection() {
//        return mLightDirection;
//    }
}
