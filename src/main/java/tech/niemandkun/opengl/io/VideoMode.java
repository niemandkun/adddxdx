package tech.niemandkun.opengl.io;

public class VideoMode {
    private final int mWidth;
    private final int mHeight;
    private final int mRefreshRate;

    public VideoMode(int width, int height, int refreshRate) {
        mWidth = width;
        mHeight = height;
        mRefreshRate = refreshRate;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getRefreshRate() {
        return mRefreshRate;
    }
}
