package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import tech.niemandkun.opengl.graphics.support.*;
import tech.niemandkun.opengl.math.Size;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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
    private final Material mSkyMaterial;

    // DEBUG: /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final Material mDebugGuiMaterial;

    private int mQuadObjectHandle;
    private int mQuadBufferHandle;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    GlRenderer(RenderTarget renderTarget, MaterialFactory materialFactory) {
        mDefaultMaterial = materialFactory.get(DefaultMaterial.class);
        mShadowMaterial = materialFactory.get(ShadowMaterial.class);
        mSkyMaterial = materialFactory.get(SkyMaterial.class);

        mOutputRenderTarget = renderTarget;
        mOutputRenderTarget.init();

        mShadowMap = new GlRenderTexture(Size.square(2048));
        mShadowMap.init();

        mVBOs = new HashMap<>();

        /// DEBUG: ////////////////////////////////////////////////////////////////////////////////////////////////////
        mDebugGuiMaterial = materialFactory.get(DebugGuiMaterial.class);

        mQuadObjectHandle = glGenVertexArrays();
        glBindVertexArray(mQuadObjectHandle);

        float[] quadVertexBufferData = {
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                -1.0f,  1.0f, 0.0f,
                -1.0f,  1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                1.0f,  1.0f, 0.0f,
        };

        mQuadBufferHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mQuadBufferHandle);
        glBufferData(GL_ARRAY_BUFFER, quadVertexBufferData, GL_STATIC_DRAW);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    void renderAll(@NotNull Camera camera, @Nullable Light light, @NotNull Collection<Renderable> renderables) {
        RenderSettings settings;

        // SHADOWS: ///////////////////////////////////////////////////////////////////////////////////////////////////

        mShadowMap.enable();
        mShadowMap.clear();

        if (light != null) {
            settings = RenderSettings.empty()
                    .putMaterial(mShadowMaterial)
                    .putViewMatrix(light.getViewMatrix())
                    .putProjectionMatrix(light.getProjectionMatrix())
                    .asOriginal();

            for (Renderable renderable : renderables)
                renderable.render(this, settings);
        }

        // SCENE: /////////////////////////////////////////////////////////////////////////////////////////////////////

        mOutputRenderTarget.enable();
        mOutputRenderTarget.clear();

        camera.adjustAspectRatio(mOutputRenderTarget.getSize());

        settings = RenderSettings.empty()
                .extractFromCamera(camera)
                .putShadowMapTexture(mShadowMap.bind());

        if (light != null) settings = settings.extractFromLight(light);

        settings = settings.asOriginal();

        for (Renderable renderable : renderables)
            renderable.render(this, settings);

        // SKY: ///////////////////////////////////////////////////////////////////////////////////////////////////////

        mSkyMaterial.getShader().enable();
        mSkyMaterial.setupShader(RenderSettings.empty().extractFromCamera(camera));

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, mQuadBufferHandle);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);

        // DEBUG: /////////////////////////////////////////////////////////////////////////////////////////////////////

        mDebugGuiMaterial.getShader().enable();
        mDebugGuiMaterial.setupShader(RenderSettings.empty().putShadowMapTexture(0));

        glViewport(0, 0, 128, 128);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mShadowMap.getTextureHandle());

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, mQuadBufferHandle);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);

        // DEBUG //////////////////////////////////////////////////////////////////////////////////////////////////////

        deallocateObsoleteVBOs();
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
