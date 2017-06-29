package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import tech.niemandkun.opengl.graphics.support.DefaultMaterial;
import tech.niemandkun.opengl.graphics.support.ShadowMaterial;
import tech.niemandkun.opengl.math.Size;

import java.util.*;

class GlRenderer implements Renderer {
    private class VboDescriptor {
        final GlVertexBufferObject vbo;
        boolean dirty;

        VboDescriptor(GlVertexBufferObject vbo) {
            this.vbo = vbo;
        }
    }

    private final RenderTarget mOutputRenderTarget;
    private final GlRenderTexture mShadowMap;

    private final Map<VertexArray, VboDescriptor> mVBOs;

    private final Material mDefaultMaterial;
    private final Material mShadowMaterial;

    GlRenderer(RenderTarget renderTarget, MaterialFactory materialFactory) {
        mDefaultMaterial = materialFactory.get(DefaultMaterial.class);
        mShadowMaterial = materialFactory.get(ShadowMaterial.class);

        mOutputRenderTarget = renderTarget;
        mOutputRenderTarget.init();

        mShadowMap = new GlRenderTexture(Size.square(2048));
        mShadowMap.init();

        mVBOs = new HashMap<>();
    }

    void renderAll(@NotNull Camera camera, @Nullable Light light, @NotNull Collection<Renderable> renderables) {
        prepareShadowMap(light, renderables);
        renderToOutputTarget(camera, light, renderables);
        deallocateObsoleteVBOs();
    }

    private void prepareShadowMap(Light light, Collection<Renderable> renderables) {
        mShadowMap.enable();
        mShadowMap.clear();

        if (light != null) {
            RenderSettings settings = RenderSettings.empty()
                    .putMaterial(mShadowMaterial)
                    .putViewMatrix(light.getViewMatrix())
                    .putProjectionMatrix(light.getProjectionMatrix())
                    .asOriginal();

            for (Renderable renderable : renderables)
                renderable.render(this, settings);
        }
    }

    private void renderToOutputTarget(Camera camera, Light light, Collection<Renderable> renderables) {
        mOutputRenderTarget.enable();
        mOutputRenderTarget.clear();

        camera.adjustAspectRatio(mOutputRenderTarget.getSize());

        RenderSettings settings = RenderSettings.empty()
                .extractFromCamera(camera)
                .putShadowMapTexture(mShadowMap.bind());

        if (light != null) settings = settings.extractFromLight(light);

        settings = settings.asOriginal();

        for (Renderable renderable : renderables)
            renderable.render(this, settings);
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
