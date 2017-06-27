package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.math.Size;

class VideoMode {
    private final Size mSize;
    private final int mRefreshRate;

    VideoMode(Size size, int refreshRate) {
        mSize = size;
        mRefreshRate = refreshRate;
    }

    Size getSize() {
        return mSize;
    }

    int getRefreshRate() {
        return mRefreshRate;
    }
}
