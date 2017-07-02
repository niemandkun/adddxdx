/*
 * Copyright (C) 2017 Poroshin Ivan
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.math;

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
