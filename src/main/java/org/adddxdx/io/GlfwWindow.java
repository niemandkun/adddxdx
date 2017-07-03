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

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.adddxdx.math.Size;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

class GlfwWindow implements Window {

    private long mHandle;
    private GlfwKeyboard mKeyboardInstance;
    private GlfwMouse mMouseInstance;

    GlfwWindow(@NotNull String title, @NotNull VideoMode videoMode, @Nullable WindowSettings windowSettings,
               @Nullable ContextSettings contextSettings, @Nullable FramebufferSettings framebufferSettings) {

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new RuntimeException("Unable to initialize GLFW.");

        glfwDefaultWindowHints();

        if (contextSettings != null) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, contextSettings.getMajorVersion());
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, contextSettings.getMinorVersion());
            glfwWindowHint(GLFW_OPENGL_PROFILE, contextSettings.getProfile());
        }

        if (windowSettings != null) {
            glfwWindowHint(GLFW_RESIZABLE, glfwBool(windowSettings.isResizable()));
            glfwWindowHint(GLFW_DECORATED, glfwBool(windowSettings.isDecorated()));
            glfwWindowHint(GLFW_FOCUSED, glfwBool(windowSettings.isFocused()));
            glfwWindowHint(GLFW_AUTO_ICONIFY, glfwBool(windowSettings.isAutoIconify()));
            glfwWindowHint(GLFW_FLOATING, glfwBool(windowSettings.isFloating()));
            glfwWindowHint(GLFW_MAXIMIZED, glfwBool(windowSettings.isMaximized()));
        }

        if (framebufferSettings != null) {
            glfwWindowHint(GLFW_RED_BITS, framebufferSettings.getRedBits());
            glfwWindowHint(GLFW_GREEN_BITS, framebufferSettings.getGreenBits());
            glfwWindowHint(GLFW_BLUE_BITS, framebufferSettings.getBlueBits());
            glfwWindowHint(GLFW_ALPHA_BITS, framebufferSettings.getAlphaBits());
            glfwWindowHint(GLFW_STENCIL_BITS, framebufferSettings.getStencilBits());
            glfwWindowHint(GLFW_DEPTH_BITS, framebufferSettings.getDepthBits());
            glfwWindowHint(GLFW_SAMPLES, framebufferSettings.getMultiSampling());
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        Size screenSize = videoMode.getSize();
        mHandle = glfwCreateWindow(screenSize.getWidth(), screenSize.getHeight(), title, NULL, NULL);

        System.out.println("OpenGL version: " + glfwGetVersionString());

        if (mHandle == NULL) throw new RuntimeException("Failed to create the GLFW mHandle.");

        if (windowSettings != null && windowSettings.isFullscreen()) {
            long monitorHandle = glfwGetWindowMonitor(mHandle);
            glfwSetWindowMonitor(mHandle, monitorHandle, 0, 0,
                    screenSize.getWidth(), screenSize.getHeight(), videoMode.getRefreshRate());
        }

        glfwMakeContextCurrent(mHandle);
        glfwSwapInterval(1);
        glfwShowWindow(mHandle);

        glfwSetInputMode(mHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        GL.createCapabilities();
    }

    private int glfwBool(boolean bool) {
        return bool ? GLFW_TRUE : GLFW_FALSE;
    }

    @Override
    public void update() {
        glfwSwapBuffers(mHandle);
        glfwPollEvents();
    }

    @Override
    public boolean isOpen() {
        return !glfwWindowShouldClose(mHandle);
    }

    @Override
    public Size getSize() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);

            glfwGetWindowSize(mHandle, width, height);

            return new Size(width.get(0), height.get(0));
        }
    }

    GlfwKeyboard getKeyboard() {
        if (mKeyboardInstance == null) {
            mKeyboardInstance = new GlfwKeyboard();
            glfwSetKeyCallback(mHandle, mKeyboardInstance);
        }

        return mKeyboardInstance;
    }

    GlfwMouse getMouse() {
        if (mMouseInstance == null) {
            mMouseInstance = new GlfwMouse();
            glfwSetCursorPosCallback(mHandle, mMouseInstance.getCursorPosCallback());
            glfwSetMouseButtonCallback(mHandle, mMouseInstance.getMouseButtonCallback());
            glfwSetScrollCallback(mHandle, mMouseInstance.getScrollCallback());
        }

        return mMouseInstance;
    }

    @Override
    public void destroy() {
        glfwFreeCallbacks(mHandle);
        glfwDestroyWindow(mHandle);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
