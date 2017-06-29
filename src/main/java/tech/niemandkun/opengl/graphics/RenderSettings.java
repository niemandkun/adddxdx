package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Matrix4;
import tech.niemandkun.opengl.math.Vector3;

public class RenderSettings {
    private final RenderSettings mOriginal;

    private final Matrix4 mModelMatrix;
    private final Matrix4 mViewMatrix;
    private final Matrix4 mProjectionMatrix;

    private final Material mMaterial;
    private final Matrix4 mLightMatrix;
    private final Vector3 mLightDirection;

    private final int mShadowMapTexture;

    private RenderSettings(Matrix4 modelMatrix, Matrix4 viewMatrix, Matrix4 projectionMatrix,
                           Matrix4 lightMatrix, Material material, Vector3 lightDirection,
                           int shadowMapTexture, RenderSettings original) {

        mProjectionMatrix = projectionMatrix;
        mModelMatrix = modelMatrix;
        mLightMatrix = lightMatrix;
        mViewMatrix = viewMatrix;
        mMaterial = material;
        mOriginal = original;
        mLightDirection = lightDirection;
        mShadowMapTexture = shadowMapTexture;
    }

    public RenderSettings getOriginal() { return mOriginal; }
    public Matrix4 getProjectionMatrix() { return mProjectionMatrix; }
    public Matrix4 getModelMatrix() { return mModelMatrix; }
    public Matrix4 getLightMatrix() { return mLightMatrix; }
    public Matrix4 getViewMatrix() { return mViewMatrix; }
    public Material getMaterial() { return mMaterial; }
    public Vector3 getLightDirection() { return mLightDirection; }
    public int getShadowMapTexture() { return mShadowMapTexture; }

    public static RenderSettings empty() {
        return new RenderSettings(null, null, null, null, null, null, -1, null);
    }

    public RenderSettings putModelMatrix(Matrix4 modelMatrix) {
        return new RenderSettings(modelMatrix, mViewMatrix, mProjectionMatrix, mLightMatrix,
                mMaterial, mLightDirection, mShadowMapTexture, mOriginal);
    }

    public RenderSettings putViewMatrix(Matrix4 viewMatrix) {
        return new RenderSettings(mModelMatrix, viewMatrix, mProjectionMatrix, mLightMatrix,
                mMaterial, mLightDirection, mShadowMapTexture, mOriginal);
    }

    public RenderSettings putProjectionMatrix(Matrix4 projectionMatrix) {
        return new RenderSettings(mModelMatrix, mViewMatrix, projectionMatrix, mLightMatrix,
                mMaterial, mLightDirection, mShadowMapTexture, mOriginal);
    }

    public RenderSettings putLightMatrix(Matrix4 lightMatrix) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, lightMatrix,
                mMaterial, mLightDirection, mShadowMapTexture, mOriginal);
    }

    public RenderSettings putMaterial(Material material) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mLightMatrix,
                material, mLightDirection, mShadowMapTexture, mOriginal);
    }

    public RenderSettings putLightDirection(Vector3 lightDirection) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mLightMatrix,
                mMaterial, lightDirection, mShadowMapTexture, mOriginal);
    }

    public RenderSettings putShadowMapTexture(int shadowMapTexture) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mLightMatrix,
                mMaterial, mLightDirection, shadowMapTexture, mOriginal);
    }

    public RenderSettings extractFromCamera(Camera camera) {
        return new RenderSettings(mModelMatrix, camera.getViewMatrix(), camera.getProjectionMatrix(), mLightMatrix,
                mMaterial, mLightDirection, mShadowMapTexture, mOriginal);
    }

    public RenderSettings extractFromLight(Light light) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix,
                light.getProjectionMatrix().cross(light.getViewMatrix()),
                mMaterial, light.getActor().getTransform().getViewDirection(),
                mShadowMapTexture, mOriginal);
    }

    RenderSettings asOriginal() {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mLightMatrix,
                mMaterial, mLightDirection, mShadowMapTexture, this);
    }
}
