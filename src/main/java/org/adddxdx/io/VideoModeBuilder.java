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

import org.adddxdx.math.Size;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class VideoModeBuilder {
    private Size mSize = new Size(800, 600);
    private int mRefreshRate = GLFW_DONT_CARE;
    private boolean mIsVsyncEnabled = true;

    private final PlatformBuilder mBuilder;

    VideoModeBuilder(PlatformBuilder builder) {
        mBuilder = builder;
    }

    public WindowSettingsBuilder onWindow() { return mBuilder.onWindow(); }
    public FramebufferSettingsBuilder onFramebuffer() { return mBuilder.onFramebuffer(); }
    public ContextSettingsBuilder onContext() { return mBuilder.onContext(); }
    public Platform build() { return mBuilder.build(); }

    VideoMode getVideoMode() {
        return new VideoMode(mSize, mRefreshRate, mIsVsyncEnabled);
    }

    public VideoModeBuilder setSize(Size size) {
        mSize = size;
        return this;
    }

    public VideoModeBuilder setRefreshRate(int refreshRate) {
        mRefreshRate = refreshRate;
        return this;
    }

    public VideoModeBuilder setVsyncEnabled(boolean vsyncEnabled) {
        mIsVsyncEnabled = vsyncEnabled;
        return this;
    }
}
