package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.ActiveSystem;
import tech.niemandkun.opengl.io.Window;
import tech.niemandkun.opengl.math.Matrix4;
import tech.niemandkun.opengl.math.Size;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class GraphicsSystem extends ActiveSystem<GraphicsSystem.Component> {
    public abstract static class Component extends tech.niemandkun.opengl.engine.Component {
        abstract void connect(GraphicsSystem system);
        abstract void disconnect(GraphicsSystem system);
    }

    private final Set<Renderer> mRenderers;

    // TODO:
    // private final Set<Light> mLights;

    private final RenderTarget mRenderTarget;
    private final Shader mShadowShader;
    private final Shader mDefaultShader;
    private final Shader mDebugShader;
    private final Window mWindow;

    private Light mLight;
    private Camera mCamera;
    private GlRenderTexture mShadowMap;

    // DEBUG:
    private int mQuadObjectHandle;
    private int mQuadBufferHandle;

    public GraphicsSystem(Window window, ShaderFactory shaderFactory) {
        mRenderers = new HashSet<>();
        mWindow = window;

        mRenderTarget = new GlWindowRenderTarget(mWindow);
        mRenderTarget.init();

        mShadowMap = new GlRenderTexture(Size.square(2048));
        mShadowMap.init();

        mShadowShader = shaderFactory.buildShader("shadow");
        mDefaultShader = shaderFactory.getDefaultMaterial().getShader();

        /// DEBUG:
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

        mDebugShader = shaderFactory.buildShader("debug");
    }

    void setCamera(Camera camera) { mCamera = camera; }
    Camera getCamera() { return mCamera; }

    void setLight(Light light) { mLight = light; }
    Light getLight() { return mLight; }

    void addRenderer(Renderer renderer) { mRenderers.add(renderer); }
    void removeRenderer(Renderer renderer) { mRenderers.remove(renderer); }

    @Override
    public void register(Component component) {
        component.connect(this);
    }

    @Override
    public void unregister(Component component) {
        component.disconnect(this);
    }

    @Override
    public void update(Duration timeSinceLastUpdate) {
        if (mCamera == null) return;

        RenderSettings settings;

        mShadowMap.enable();
        mShadowMap.clear();

        mShadowShader.enable();

        Matrix4 lightMpv = mLight.getMatrix();

        settings = new RenderSettings(lightMpv);

        for (Renderer renderer : mRenderers)
            renderer.render(mShadowMap, settings);

        mRenderTarget.enable();
        mRenderTarget.clear();

        mCamera.adjustAspectRatio(mWindow.getSize());

        settings = new RenderSettings(mCamera.getMvpMatrix(), mCamera.getViewMatrix(), lightMpv);

        mDefaultShader.enable();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mShadowMap.getTextureHandle());
        mDefaultShader.setUniform("shadowMap", 0);
        mDefaultShader.setUniform("lightDirection", mLight.getActor().getTransform().getViewDirection());
        mDefaultShader.setUniform("vMatrix", mCamera.getViewMatrix());

        for (Renderer renderer : mRenderers)
            renderer.render(mRenderTarget, settings);

        // DEBUG: /////////////////////////////////////////////////////////////////////////////////////////////////////

        // Render only on a corner of the window (or we we won't see the real rendering...)
        glViewport(0, 0, 128, 128);

        // Use our shader
        mDebugShader.enable();

        // Bind our texture in Texture Unit 0
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mShadowMap.getTextureHandle());
        // Set our "renderedTexture" sampler to user Texture Unit 0
        mDebugShader.setUniform("mtexture", 0);

        // 1rst attribute buffer : vertices
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, mQuadBufferHandle);
        glVertexAttribPointer(
                0,                  // attribute 0. No particular reason for 0, but must match the layout in the shader.
                3,                  // size
                GL_FLOAT,           // type
                false,           // normalized?
                0,                  // stride
                0            // array buffer offset
		);

        // Draw the triangle !
        // You have to disable GL_COMPARE_R_TO_TEXTURE above in order to see anything !
        glDrawArrays(GL_TRIANGLES, 0, 6); // 2*3 indices starting at 0 -> 2 triangles
        //glDisableVertexAttribArray(0);

        // DEBUG: /////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private final static Matrix4 BIAS_MATRIX = new Matrix4(
            0.5f, 0, 0, 0.5f,
            0, 0.5f, 0, 0.5f,
            0, 0, 0.5f, 0.5f,
            0, 0, 0, 1
    );
}
