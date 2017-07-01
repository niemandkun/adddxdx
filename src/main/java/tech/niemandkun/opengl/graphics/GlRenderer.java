package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.graphics.support.materials.DefaultMaterial;
import tech.niemandkun.opengl.graphics.support.materials.ShadowMaterial;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitiveType;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitivesFactory;
import tech.niemandkun.opengl.math.Size;

import java.util.*;

class GlRenderer implements Renderer {
    private final static String SCREEN_SHADER_NAME = "screen";
    private final static String SCREEN_SHADER_TEXTURE = "screenTexture";
    private final static int PIXEL_EFFECT_LEVEL = 5;

    private final Shader mScreenShader;
    private final GlVertexBufferObject mScreenObject;

    private class VboDescriptor {
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

    GlRenderer(RenderTarget renderTarget, MaterialFactory materialFactory, PrimitivesFactory primitivesFactory) {
        mDefaultMaterial = materialFactory.get(DefaultMaterial.class);
        mShadowMaterial = materialFactory.get(ShadowMaterial.class);
        mScreenShader = materialFactory.getShader(ShaderDescription.forFile(SCREEN_SHADER_NAME));

        mOutputRenderTarget = renderTarget;
        mOutputRenderTarget.init();

        mShadowMap = new GlRenderTexture(new GlDepthTexture(Size.square(2048)));
        mShadowMap.init();

        Size internalRenderSize = renderTarget.getSize().shrink(PIXEL_EFFECT_LEVEL);
        mInternalRenderTarget = new GlRenderTexture(new GlRgbTexture(internalRenderSize));
        mInternalRenderTarget.init();

        mScreenObject = new GlVertexBufferObject(primitivesFactory.create(PrimitiveType.QUAD).getVertexArray());

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
                    .putMaterial(mShadowMaterial)
                    .putViewMatrix(light.getViewMatrix())
                    .putProjectionMatrix(light.getProjectionMatrix())
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
                .extractFromCamera(camera)
                .putShadowMapTexture(mShadowMap.getTexture().bind(0));

        newSettings = newSettings.asOriginal();

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
