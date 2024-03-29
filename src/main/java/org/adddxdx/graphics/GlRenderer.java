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

import org.adddxdx.engine.Resources;
import org.adddxdx.graphics.support.materials.DefaultMaterial;
import org.adddxdx.graphics.support.materials.ShadowMaterial;
import org.adddxdx.graphics.support.primitives.PrimitiveType;
import org.adddxdx.math.Size;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class GlRenderer implements Renderer {
    private final static String SCREEN_SHADER_NAME = "screen";
    private final static String SCREEN_SHADER_TEXTURE = "screenTexture";

    private final static Size INTERNAL_RESOLUTION = new Size(480, 360);
    private final static float INTERNAL_ASPECT_RATIO = 4 / 3.0f; // used only to calculate aspect ratio of virtual pixels

    private final Shader mScreenShader;
    private final GlVertexBufferObject mScreenObject;

    private static class VboDescriptor {
        final GlVertexBufferObject vbo;
        boolean dirty;

        VboDescriptor(GlVertexBufferObject vbo) {
            this.vbo = vbo;
        }
    }

    private final RenderTarget mOutputRenderTarget;
    private final GlRenderTexture mShadowMap;
    private final GlRenderTexture mInternalRenderTarget;

    private final Map<VertexArray, VboDescriptor> mVBOs;

    private final Material mDefaultMaterial;
    private final Material mShadowMaterial;

    GlRenderer(RenderTarget renderTarget, Resources resources) {
        mDefaultMaterial = resources.getMaterial(DefaultMaterial.class);
        mShadowMaterial = resources.getMaterial(ShadowMaterial.class);
        mScreenShader = resources.getShader(ShaderDescription.forFile(SCREEN_SHADER_NAME));

        mOutputRenderTarget = renderTarget;
        mOutputRenderTarget.init();

        mShadowMap = new GlRenderTexture(new GlDepthTexture(Size.square(2048), Texture.WrapMode.CLAMP_TO_BORDER));
        mShadowMap.init();

        float pixelRatio = INTERNAL_ASPECT_RATIO * INTERNAL_RESOLUTION.getHeight() / INTERNAL_RESOLUTION.getWidth();
        float internalToExternalRatio = (float) INTERNAL_RESOLUTION.getHeight() / renderTarget.getSize().getHeight();
        int internalScreenWidth = Math.round(renderTarget.getSize().getWidth() * internalToExternalRatio / pixelRatio);

        Size internalRenderSize = new Size(internalScreenWidth, INTERNAL_RESOLUTION.getHeight());
        mInternalRenderTarget = new GlRenderTexture(new GlRgbTexture(internalRenderSize));
        mInternalRenderTarget.init();

        mScreenObject = new GlVertexBufferObject(resources.getPrimitive(PrimitiveType.QUAD).getVertexArray());

        mVBOs = new HashMap<>();
    }

    void renderAll(Camera camera, RenderSettings settings, Collection<Renderable> renderables) {
        prepareShadowMap(settings.getLight(), renderables);
        renderToInternalTarget(camera, settings, renderables);
        renderToOutputTarget();
        deallocateObsoleteVBOs();
    }

    private void prepareShadowMap(Light light, Collection<Renderable> renderables) {
        mShadowMap.enable();
        mShadowMap.clear();

        if (light != null) {
            RenderSettings settings = RenderSettings.forShadowPass()
                    .putView(light)
                    .putMaterial(mShadowMaterial)
                    .asOriginal();

            for (Renderable renderable : renderables)
                renderable.render(this, settings);
        }
    }

    private void renderToInternalTarget(Camera camera, RenderSettings settings, Collection<Renderable> renderables) {
        mInternalRenderTarget.enable();
        mInternalRenderTarget.clear();

        camera.adjustAspectRatio(mOutputRenderTarget.getSize());

        RenderSettings newSettings = settings
                .putView(camera)
                .putShadowMapTexture(mShadowMap.getTexture().bind(0))
                .asOriginal();

        for (Renderable renderable : renderables)
            renderable.render(this, newSettings);
    }

    private void renderToOutputTarget() {
        mOutputRenderTarget.enable();
        mOutputRenderTarget.clear();

        mScreenShader.enable();
        mScreenShader.setUniform(SCREEN_SHADER_TEXTURE, mInternalRenderTarget.getTexture().bind(0));

        if (!mScreenObject.isAllocated())
            mScreenObject.allocate();

        mScreenObject.draw();
    }

    private void deallocateObsoleteVBOs() {
        for (Map.Entry<VertexArray, VboDescriptor> entry: mVBOs.entrySet()) {
            if (!entry.getValue().dirty) {
                mVBOs.remove(entry.getKey());
                entry.getValue().vbo.deallocate();
            }
        }
    }

    @Override
    public void render(VertexArray vertices, RenderSettings settings) {
        VboDescriptor vboDescriptor = mVBOs.get(vertices);
        if (vboDescriptor == null) vboDescriptor = createVboDescriptor(vertices);

        vboDescriptor.dirty = true;
        mVBOs.put(vertices, vboDescriptor);

        Material renderMaterial = getRenderMaterial(settings);
        renderMaterial.getShader().enable();
        renderMaterial.setupShader(settings);

        if (!vboDescriptor.vbo.isAllocated())
            vboDescriptor.vbo.allocate();

        vboDescriptor.vbo.draw();
    }

    private Material getRenderMaterial(RenderSettings settings) {
        Material renderMaterial;

        RenderSettings rendererSettings = settings.getOriginal();

        if (rendererSettings.getMaterial() != null)
            renderMaterial = rendererSettings.getMaterial();
        else if (settings.getMaterial() != null)
            renderMaterial = settings.getMaterial();
        else
            renderMaterial = mDefaultMaterial;

        return renderMaterial;
    }

    private VboDescriptor createVboDescriptor(VertexArray vertexArray) {
        return new VboDescriptor(new GlVertexBufferObject(vertexArray));
    }
}
