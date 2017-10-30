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

public abstract class PlatformBuilder {
    private VideoModeBuilder mVideoMode = new VideoModeBuilder(this);
    private WindowSettingsBuilder mWindowSettings = new WindowSettingsBuilder(this);
    private FramebufferSettingsBuilder mFramebufferSettings = new FramebufferSettingsBuilder(this);
    private ContextSettingsBuilder mContextSettings = new ContextSettingsBuilder(this);

    public VideoModeBuilder onVideoMode() { return mVideoMode; }
    public WindowSettingsBuilder onWindow() { return mWindowSettings; }
    public FramebufferSettingsBuilder onFramebuffer() { return mFramebufferSettings; }
    public ContextSettingsBuilder onContext() { return mContextSettings; }

    public Platform andEverythingElseIsDefault() {
        return buildPlatform(
                mWindowSettings.getTitle(),
                mVideoMode.getVideoMode(),
                mWindowSettings.getSettings(),
                mContextSettings.getContextSettings(),
                mFramebufferSettings.getFramebufferSettings()
        );
    }

    abstract Platform buildPlatform(String title, VideoMode videoMode,
                                    WindowSettings windowSettings,
                                    ContextSettings contextSettings,
                                    FramebufferSettings framebufferSettings);
}
