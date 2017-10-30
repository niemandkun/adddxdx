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

public class WindowSettingsBuilder {

    private boolean mResizable = true;
    private boolean mDecorated = true;
    private boolean mFocused = true;
    private boolean mAutoIconify = true;
    private boolean mFloating = false;
    private boolean mMaximized = false;
    private boolean mFullscreen = false;

    private String title = "Window";

    private final PlatformBuilder mBuilder;

    WindowSettingsBuilder(PlatformBuilder builder) {
        mBuilder = builder;
    }

    public VideoModeBuilder onVideoMode() { return mBuilder.onVideoMode(); }
    public FramebufferSettingsBuilder onFramebuffer() { return mBuilder.onFramebuffer(); }
    public ContextSettingsBuilder onContext() { return mBuilder.onContext(); }
    public Platform andEverythingElseIsDefault() { return mBuilder.andEverythingElseIsDefault(); }

    String getTitle() {
        return title;
    }

    WindowSettings getSettings() {
        return new WindowSettings(mResizable, mDecorated, mFocused, mAutoIconify, mFloating, mMaximized, mFullscreen);
    }

    public WindowSettingsBuilder setResizable(boolean resizable) {
        mResizable = resizable;
        return this;
    }

    public WindowSettingsBuilder setDecorated(boolean decorated) {
        mDecorated = decorated;
        return this;
    }

    public WindowSettingsBuilder setFocused(boolean focused) {
        mFocused = focused;
        return this;
    }

    public WindowSettingsBuilder setAutoIconify(boolean autoIconify) {
        mAutoIconify = autoIconify;
        return this;
    }

    public WindowSettingsBuilder setFloating(boolean floating) {
        mFloating = floating;
        return this;
    }

    public WindowSettingsBuilder setMaximized(boolean maximized) {
        mMaximized = maximized;
        return this;
    }

    public WindowSettingsBuilder setFullscreen(boolean fullscreen) {
        mFullscreen = fullscreen;
        return this;
    }

    public WindowSettingsBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
}
