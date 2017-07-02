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

package org.adddxdx.io;

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
