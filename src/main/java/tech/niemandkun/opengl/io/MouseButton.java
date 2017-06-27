package tech.niemandkun.opengl.io;

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
