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

public class FramebufferSettingsBuilder {
    private int mRedBits = 8;
    private int mGreenBits = 8;
    private int mBlueBits = 8;
    private int mAlphaBits = 8;
    private int mDepthBits = 24;
    private int mStencilBits = 8;
    private int mMultiSampling = 0;

    private final PlatformBuilder mBuilder;

    FramebufferSettingsBuilder(PlatformBuilder builder) {
        mBuilder = builder;
    }

    public VideoModeBuilder onVideoMode() { return mBuilder.onVideoMode(); }
    public WindowSettingsBuilder onWindow() { return mBuilder.onWindow(); }
    public ContextSettingsBuilder onContext() { return mBuilder.onContext(); }
    public Platform andEverythingElseIsDefault() { return mBuilder.andEverythingElseIsDefault(); }

    FramebufferSettings getFramebufferSettings() {
        return new FramebufferSettings(mRedBits, mGreenBits, mBlueBits, mAlphaBits,
                mDepthBits, mStencilBits, mMultiSampling);
    }

    public FramebufferSettingsBuilder setRedBits(int redBits) {
        mRedBits = redBits;
        return this;
    }

    public FramebufferSettingsBuilder setGreenBits(int greenBits) {
        mGreenBits = greenBits;
        return this;
    }

    public FramebufferSettingsBuilder setBlueBits(int blueBits) {
        mBlueBits = blueBits;
        return this;
    }

    public FramebufferSettingsBuilder setAlphaBits(int alphaBits) {
        mAlphaBits = alphaBits;
        return this;
    }

    public FramebufferSettingsBuilder setDepthBits(int depthBits) {
        mDepthBits = depthBits;
        return this;
    }

    public FramebufferSettingsBuilder setStencilBits(int stencilBits) {
        mStencilBits = stencilBits;
        return this;
    }

    public FramebufferSettingsBuilder setMultiSampling(int multiSampling) {
        mMultiSampling = multiSampling;
        return this;
    }
}
