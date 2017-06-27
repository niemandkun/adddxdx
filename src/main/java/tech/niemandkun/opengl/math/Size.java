package tech.niemandkun.opengl.math;

public class Size {
    private final int mWidth;
    private final int mHeight;

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public Size(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public static Size square(int size) {
        return new Size(size, size);
    }
}
