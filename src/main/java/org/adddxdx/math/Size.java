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
