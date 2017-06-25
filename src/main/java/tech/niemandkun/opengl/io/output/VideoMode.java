package tech.niemandkun.opengl.io.output;

class VideoMode {
    private final int mWidth;
    private final int mHeight;
    private final int mRefreshRate;

    VideoMode(int width, int height, int refreshRate) {
        mWidth = width;
        mHeight = height;
        mRefreshRate = refreshRate;
    }

    int getWidth() {
        return mWidth;
    }

    int getHeight() {
        return mHeight;
    }

    int getRefreshRate() {
        return mRefreshRate;
    }
}
