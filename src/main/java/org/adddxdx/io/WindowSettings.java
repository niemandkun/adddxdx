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

class WindowSettings {
    private final boolean mResizable;
    private final boolean mDecorated;
    private final boolean mFocused;
    private final boolean mAutoIconify;
    private final boolean mFloating;
    private final boolean mMaximized;
    private final boolean mFullscreen;

    WindowSettings(
            boolean resizable, boolean decorated, boolean focused, boolean autoIconify,
            boolean floating, boolean maximized, boolean fullscreen) {

        mResizable = resizable;
        mDecorated = decorated;
        mFocused = focused;
        mAutoIconify = autoIconify;
        mFloating = floating;
        mMaximized = maximized;
        mFullscreen = fullscreen;
    }

    boolean isResizable() {
        return mResizable;
    }

    boolean isDecorated() {
        return mDecorated;
    }

    boolean isFocused() {
        return mFocused;
    }

    boolean isAutoIconify() {
        return mAutoIconify;
    }

    boolean isFloating() {
        return mFloating;
    }

    boolean isMaximized() {
        return mMaximized;
    }

    boolean isFullscreen() {
        return mFullscreen;
    }
}
