package tech.niemandkun.opengl.io.output;

class FramebufferSettings {
    private final int mRedBits;
    private final int mGreenBits;
    private final int mBlueBits;
    private final int mAlphaBits;
    private final int mDepthBits;
    private final int mStencilBits;
    private final int mMultiSampling;

    FramebufferSettings(int redBits, int greenBits, int blueBits, int alphaBits,
                        int depthBits, int stencilBits, int multiSampling) {
        mRedBits = redBits;
        mGreenBits = greenBits;
        mBlueBits = blueBits;
        mAlphaBits = alphaBits;
        mDepthBits = depthBits;
        mStencilBits = stencilBits;
        mMultiSampling = multiSampling;
    }

    int getRedBits() {
        return mRedBits;
    }

    int getGreenBits() {
        return mGreenBits;
    }

    int getBlueBits() {
        return mBlueBits;
    }

    int getAlphaBits() {
        return mAlphaBits;
    }

    int getDepthBits() {
        return mDepthBits;
    }

    int getStencilBits() {
        return mStencilBits;
    }

    int getMultiSampling() {
        return mMultiSampling;
    }
}
