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

public class ContextSettingsBuilder {
    private int mMajorVersion = 3;
    private int mMinorVersion = 0;
    private ContextProfile mProfile = ContextProfile.ANY;

    private final PlatformBuilder mBuilder;

    ContextSettingsBuilder(PlatformBuilder builder) {
        mBuilder = builder;
    }

    public VideoModeBuilder onVideoMode() { return mBuilder.onVideoMode(); }
    public WindowSettingsBuilder onWindow() { return mBuilder.onWindow(); }
    public FramebufferSettingsBuilder onFramebuffer() { return mBuilder.onFramebuffer(); }
    public Platform andEverythingElseIsDefault() { return mBuilder.andEverythingElseIsDefault(); }

    ContextSettings getContextSettings() {
        return new ContextSettings(mMajorVersion, mMinorVersion, mProfile.getProfile());
    }

    public ContextSettingsBuilder setMajorVersion(int majorVersion) {
        mMajorVersion = majorVersion;
        return this;
    }

    public ContextSettingsBuilder setMinorVersion(int minorVersion) {
        mMinorVersion = minorVersion;
        return this;
    }

    public ContextSettingsBuilder setProfile(ContextProfile profile) {
        mProfile = profile;
        return this;
    }
}
