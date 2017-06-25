package tech.niemandkun.opengl.io.builders;

import tech.niemandkun.opengl.io.FramebufferSettings;
import tech.niemandkun.opengl.io.Window;

public class FramebufferSettingsBuilder {
    private int mRedBits = 8;
    private int mGreenBits = 8;
    private int mBlueBits = 8;
    private int mAlphaBits = 8;
    private int mDepthBits = 24;
    private int mStencilBits = 8;
    private int mMultiSampling = 0;

    private final WindowBuilder mBuilder;

    FramebufferSettingsBuilder(WindowBuilder builder) {
        mBuilder = builder;
    }

    public VideoModeBuilder onVideoMode() { return mBuilder.onVideoMode(); }
    public WindowSettingsBuilder onWindow() { return mBuilder.onWindow(); }
    public ContextSettingsBuilder onContext() { return mBuilder.onContext(); }
    public Window build() { return mBuilder.build(); }

    FramebufferSettings getFramebufferSettings() {
        return new FramebufferSettings(mRedBits, mGreenBits, mBlueBits, mAlphaBits,
                mDepthBits, mStencilBits, mMultiSampling);
    }

    public FramebufferSettingsBuilder setRedBits(int redBits) {
        mRedBits = redBits;
        return this;
    }

    public FramebufferSettingsBuilder setGreenBits(int greenBits) {
        mGreenBits = greenBits;
        return this;
    }

    public FramebufferSettingsBuilder setBlueBits(int blueBits) {
        mBlueBits = blueBits;
        return this;
    }

    public FramebufferSettingsBuilder setAlphaBits(int alphaBits) {
        mAlphaBits = alphaBits;
        return this;
    }

    public FramebufferSettingsBuilder setDepthBits(int depthBits) {
        mDepthBits = depthBits;
        return this;
    }

    public FramebufferSettingsBuilder setStencilBits(int stencilBits) {
        mStencilBits = stencilBits;
        return this;
    }

    public FramebufferSettingsBuilder setMultiSampling(int multiSampling) {
        mMultiSampling = multiSampling;
        return this;
    }
}
