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

public enum ContextProfile {
    CORE(GLFW_OPENGL_CORE_PROFILE),
    ANY(GLFW_OPENGL_ANY_PROFILE),
    COMPAT(GLFW_OPENGL_COMPAT_PROFILE);

    private final int mProfile;

    ContextProfile(int profile) {
        mProfile = profile;
    }

    int getProfile() {
        return mProfile;
    }
}
