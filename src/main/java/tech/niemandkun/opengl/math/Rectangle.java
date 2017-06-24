package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Rectangle {
    private float mLeft;
    private float mRight;
    private float mTop;
    private float mBottom;

    public float getWidth() {
        return mRight - mLeft;
    }

    public float getHeight() {
        return mBottom - mTop;
    }

    public Rectangle(int left, int right, int top, int bottom) {
        mLeft = left;
        mRight = right;
        mTop = top;
        mBottom = bottom;
    }

    public boolean isCorrect()
    {
        return mLeft < mRight && mTop < mBottom;
    }

    public float getLeft() {
        return mLeft;
    }

    public void setLeft(int left) {
        mLeft = left;
    }

    public float getRight() {
        return mRight;
    }

    public void setRight(int right) {
        mRight = right;
    }

    public float getTop() {
        return mTop;
    }

    public void setTop(int top) {
        mTop = top;
    }

    public float getBottom() {
        return mBottom;
    }

    public void setBottom(int bottom) {
        mBottom = bottom;
    }

    public @NotNull Vector2 getCenter() {
        return new Vector2((mLeft + mRight)/2, (mTop + mBottom)/2);
    }

    public @NotNull Vector2 getTopLeft() {
        return new Vector2(mLeft, mTop);
    }

    public @NotNull Vector2 getBottomRight() {
        return new Vector2(mRight, mBottom);
    }

    public void setTopLeft(@NotNull Vector2 v) {
        mTop = v.getY();
        mLeft = v.getX();
    }

    public void setBottomRight(@NotNull Vector2 v) {
        mBottom = v.getY();
        mRight = v.getX();
    }
}
