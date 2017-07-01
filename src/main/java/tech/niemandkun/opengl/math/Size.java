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

    public Size shrink(int times) {
        return new Size(mWidth / times, mHeight / times);
    }

    public Size grow(int times) {
        return new Size(mWidth * times, mHeight * times);
    }

    public static Size square(int size) {
        return new Size(size, size);
    }
}
