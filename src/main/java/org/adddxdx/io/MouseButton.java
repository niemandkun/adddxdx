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

public class MouseButton {
    private MouseButton() { }

    public static final int
            UNKNOWN             = GLFW_KEY_UNKNOWN;

    public static final int
            BUTTON_1            = GLFW_MOUSE_BUTTON_1,
            BUTTON_2            = GLFW_MOUSE_BUTTON_2,
            BUTTON_3            = GLFW_MOUSE_BUTTON_3,
            BUTTON_4            = GLFW_MOUSE_BUTTON_4,
            BUTTON_5            = GLFW_MOUSE_BUTTON_5,
            BUTTON_6            = GLFW_MOUSE_BUTTON_6,
            BUTTON_7            = GLFW_MOUSE_BUTTON_7,
            BUTTON_8            = GLFW_MOUSE_BUTTON_8,
            LAST                = GLFW_MOUSE_BUTTON_LAST,
            LEFT                = GLFW_MOUSE_BUTTON_LEFT,
            RIGHT               = GLFW_MOUSE_BUTTON_RIGHT,
            MIDDLE              = GLFW_MOUSE_BUTTON_MIDDLE;
}
