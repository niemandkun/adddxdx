/*
 * Copyright (C) 2017 Poroshin Ivan
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.graphics;

import org.adddxdx.math.Matrix4;

public class RenderSettings {
    private final RenderSettings mOriginal;

    private final Matrix4 mModelMatrix;

    private final Material mMaterial;
    private final Light mLight;
    private final View mView;
    private final Fog mFog;

    private final int mShadowMapTexture;
    private final boolean mShadowPass;

    private RenderSettings(RenderSettings original, View view, Material material, Light light, Fog fog,
                           Matrix4 modelMatrix, int shadowMapTexture, boolean shadowPass) {

        mShadowMapTexture = shadowMapTexture;
        mModelMatrix = modelMatrix;
        mShadowPass = shadowPass;
        mMaterial = material;
        mOriginal = original;
        mLight = light;
        mView = view;
        mFog = fog;
    }

    public int getShadowMapTexture() { return mShadowMapTexture; }
    public RenderSettings getOriginal() { return mOriginal; }
    public boolean isShadowPass() { return mShadowPass; }
    public Material getMaterial() { return mMaterial; }
    public Matrix4 getModelMatrix() { return mModelMatrix; }
    public Light getLight() { return mLight; }
    public View getView() { return mView; }
    public Fog getFog() { return mFog; }

    static RenderSettings empty() {
        return new RenderSettings(null, null, null, null, null, null, -1, false);
    }

    static RenderSettings forShadowPass() {
        return new RenderSettings(null, null, null, null, null, null, -1, true);
    }

    RenderSettings asOriginal() {
        return new RenderSettings(this, mView, mMaterial, mLight, mFog, mModelMatrix,
                mShadowMapTexture, mShadowPass);
    }

    public RenderSettings putView(View view) {
        return new RenderSettings(mOriginal, view, mMaterial, mLight, mFog, mModelMatrix,
                mShadowMapTexture, mShadowPass);
    }

    public RenderSettings putMaterial(Material material) {
        return new RenderSettings(mOriginal, mView, material, mLight, mFog, mModelMatrix,
                mShadowMapTexture, mShadowPass);
    }

    public RenderSettings putLight(Light light) {
        return new RenderSettings(mOriginal, mView, mMaterial, light, mFog, mModelMatrix,
                mShadowMapTexture, mShadowPass);
    }

    public RenderSettings putFog(Fog fog) {
        return new RenderSettings(mOriginal, mView, mMaterial, mLight, fog, mModelMatrix,
                mShadowMapTexture, mShadowPass);
    }

    public RenderSettings putModelMatrix(Matrix4 modelMatrix) {
        return new RenderSettings(mOriginal, mView, mMaterial, mLight, mFog, modelMatrix,
                mShadowMapTexture, mShadowPass);
    }

    public RenderSettings putShadowMapTexture(int shadowMapTexture) {
        return new RenderSettings(mOriginal, mView, mMaterial, mLight, mFog, mModelMatrix,
                shadowMapTexture, mShadowPass);
    }

}
