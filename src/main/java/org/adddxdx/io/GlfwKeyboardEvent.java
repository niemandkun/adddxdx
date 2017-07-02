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

import static org.lwjgl.glfw.GLFW.*;

class GlfwKeyboardEvent implements KeyboardEvent {
    private final int mAction;
    private final int mKey;
    private final int mScancode;
    private final int mModifiers;
    private final boolean mRepeated;

    GlfwKeyboardEvent(int action, int key, int scancode, int modifiers, boolean isRepeated) {
        mAction = action;
        mKey = key;
        mScancode = scancode;
        mModifiers = modifiers;
        mRepeated = isRepeated;
    }

    int getAction() {
        return mAction;
    }

    @Override
    public int getScancode() {
        return mScancode;
    }

    @Override
    public int getKey() {
        return mKey;
    }

    @Override
    public boolean isRepeated() {
        return mRepeated;
    }

    @Override
    public boolean isShiftPressed() {
        return (mModifiers & GLFW_MOD_SHIFT) != 0;
    }

    @Override
    public boolean isControlPressed() {
        return (mModifiers & GLFW_MOD_CONTROL) != 0;
    }

    @Override
    public boolean isAltPressed() {
        return (mModifiers & GLFW_MOD_ALT) != 0;
    }
}
