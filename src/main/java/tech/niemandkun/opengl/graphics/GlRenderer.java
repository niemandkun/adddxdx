package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import tech.niemandkun.opengl.graphics.support.*;
import tech.niemandkun.opengl.math.Size;
import tech.niemandkun.opengl.math.Vector3;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

class GlRenderer implements Renderer {
    private final int mFramebufferHandle;
    private final int mTextureHandle;
    private final int mWidth;
    private final int mHeight;
    private final int mDepthRenderBuffer;
    private final Material mQuadMaterial;
    private final GlVertexBufferObject mQuad;

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
        mQuadMaterial = materialFactory.get(QuadMaterial.class);

        mOutputRenderTarget = renderTarget;
        mOutputRenderTarget.init();

        mShadowMap = new GlRenderTexture(Size.square(2048));
        mShadowMap.init();

        mVBOs = new HashMap<>();

        // NEW: ///////////////////////////////////////////////////////////////////////////////////////////////////////

        //mWidth = 568;
        //mHeight = 320;

        mWidth = renderTarget.getSize().getWidth() / 5;
        mHeight = renderTarget.getSize().getHeight() / 5;

        mFramebufferHandle = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, mFramebufferHandle);

        mTextureHandle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, mTextureHandle);
        glTexImage2D(GL_TEXTURE_2D, 0,  GL_RGB, mWidth, mHeight, 0, GL_RGB, GL_FLOAT, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, mTextureHandle, 0);

//        mDepthRenderBuffer = 0;
        mDepthRenderBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, mDepthRenderBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, mWidth, mHeight);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, mDepthRenderBuffer);


        glDrawBuffer(GL_COLOR_ATTACHMENT0);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            throw new IllegalStateException("Error initializing framebuffer for render texture.");

        mQuad = new GlVertexBufferObject(new VertexArray(
                new Vector3[] {
                        new Vector3(-1.0f, -1.0f, 0.0f),
                        new Vector3(1.0f, -1.0f, 0.0f),
                        new Vector3(-1.0f,  1.0f, 0.0f),
                        new Vector3(-1.0f,  1.0f, 0.0f),
                        new Vector3(1.0f, -1.0f, 0.0f),
                        new Vector3(1.0f,  1.0f, 0.0f),
                }, null, null, null)
        );

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        glBindFramebuffer(GL_FRAMEBUFFER, mFramebufferHandle);
        glViewport(0, 0, mWidth, mHeight);
        //mOutputRenderTarget.enable();
        mOutputRenderTarget.clear();

        camera.adjustAspectRatio(mOutputRenderTarget.getSize());

        RenderSettings settings = RenderSettings.empty()
                .extractFromCamera(camera)
                .putShadowMapTexture(mShadowMap.bind());

        if (light != null) settings = settings.extractFromLight(light);

        settings = settings.asOriginal();

        for (Renderable renderable : renderables)
            renderable.render(this, settings);

        mOutputRenderTarget.enable();
        mOutputRenderTarget.clear();
//
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mTextureHandle);
        mQuadMaterial.getShader().enable();
        mQuadMaterial.setupShader(RenderSettings.empty().putShadowMapTexture(0));

        if (!mQuad.isAllocated()) mQuad.allocate();
        mQuad.draw();

        System.out.println(glGetError());
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
