package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Matrix4;

public class RenderSettings {
    private final RenderSettings mOriginal;

    private final Matrix4 mModelMatrix;
    private final Matrix4 mViewMatrix;
    private final Matrix4 mProjectionMatrix;

    private final Material mMaterial;
    private final Light mLight;
    private final Fog mFog;

    private final int mShadowMapTexture;
    private final boolean mShadowPass;

    private RenderSettings(Matrix4 modelMatrix, Matrix4 viewMatrix, Matrix4 projectionMatrix,
                           Material material, Light light, Fog fog, int shadowMapTexture,
                           RenderSettings original, boolean shadowPass) {

        mShadowMapTexture = shadowMapTexture;
        mProjectionMatrix = projectionMatrix;
        mModelMatrix = modelMatrix;
        mShadowPass = shadowPass;
        mViewMatrix = viewMatrix;
        mMaterial = material;
        mOriginal = original;
        mLight = light;
        mFog = fog;
    }

    public Matrix4 getProjectionMatrix() { return mProjectionMatrix; }
    public int getShadowMapTexture() { return mShadowMapTexture; }
    public RenderSettings getOriginal() { return mOriginal; }
    public Matrix4 getModelMatrix() { return mModelMatrix; }
    public Matrix4 getViewMatrix() { return mViewMatrix; }
    public boolean isShadowPass() { return mShadowPass; }
    public Material getMaterial() { return mMaterial; }
    public Light getLight() { return mLight; }
    public Fog getFog() { return mFog; }

    public static RenderSettings empty() {
        return new RenderSettings(null, null, null, null, null, null, -1, null, false);
    }

    public static RenderSettings forShadowPass() {
        return new RenderSettings(null, null, null, null, null, null, -1, null, true);
    }

    public RenderSettings putModelMatrix(Matrix4 modelMatrix) {
        return new RenderSettings(modelMatrix, mViewMatrix, mProjectionMatrix, mMaterial,
                mLight, mFog, mShadowMapTexture, mOriginal, mShadowPass);
    }

    public RenderSettings putViewMatrix(Matrix4 viewMatrix) {
        return new RenderSettings(mModelMatrix, viewMatrix, mProjectionMatrix, mMaterial,
                mLight, mFog, mShadowMapTexture, mOriginal, mShadowPass);
    }

    public RenderSettings putProjectionMatrix(Matrix4 projectionMatrix) {
        return new RenderSettings(mModelMatrix, mViewMatrix, projectionMatrix, mMaterial,
                mLight, mFog, mShadowMapTexture, mOriginal, mShadowPass);
    }

    public RenderSettings putMaterial(Material material) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, material,
                mLight, mFog, mShadowMapTexture, mOriginal, mShadowPass);
    }

    public RenderSettings putLight(Light light) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mMaterial,
                light, mFog, mShadowMapTexture, mOriginal, mShadowPass);
    }

    public RenderSettings putFog(Fog fog) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mMaterial,
                mLight, fog, mShadowMapTexture, mOriginal, mShadowPass);
    }

    public RenderSettings putShadowMapTexture(int shadowMapTexture) {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mMaterial,
                mLight, mFog, shadowMapTexture, mOriginal, mShadowPass);
    }

    public RenderSettings extractFromCamera(Camera camera) {
        return new RenderSettings(mModelMatrix, camera.getViewMatrix(), camera.getProjectionMatrix(), mMaterial,
                mLight, mFog, mShadowMapTexture, mOriginal, mShadowPass);
    }

    RenderSettings asOriginal() {
        return new RenderSettings(mModelMatrix, mViewMatrix, mProjectionMatrix, mMaterial,
                mLight, mFog, mShadowMapTexture, this, mShadowPass);
    }
}
