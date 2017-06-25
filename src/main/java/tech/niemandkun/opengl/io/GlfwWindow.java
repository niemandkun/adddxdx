package tech.niemandkun.opengl.io;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GlfwWindow implements Window {

    private long mHandle;

    public GlfwWindow(@NotNull String title, @NotNull VideoMode videoMode, @Nullable WindowSettings windowSettings,
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

        mHandle = glfwCreateWindow(videoMode.getWidth(), videoMode.getHeight(), title, NULL, NULL);

        System.out.println("OpenGL version: " + glfwGetVersionString());

        if (mHandle == NULL) throw new RuntimeException("Failed to create the GLFW mHandle.");

        if (windowSettings != null && windowSettings.isFullscreen()) {
            long monitorHandle = glfwGetWindowMonitor(mHandle);
            glfwSetWindowMonitor(mHandle, monitorHandle, 0, 0,
                    videoMode.getWidth(), videoMode.getHeight(), videoMode.getRefreshRate());
        }

        glfwMakeContextCurrent(mHandle);
        glfwSwapInterval(1);
        glfwShowWindow(mHandle);

        GL.createCapabilities();
    }

    private int glfwBool(boolean bool) {
        return bool ? GLFW_TRUE : GLFW_FALSE;
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void display() {
        glfwSwapBuffers(mHandle);
        glfwPollEvents();
    }

    @Override
    public boolean isOpen() {
        return !glfwWindowShouldClose(mHandle);
    }

    @Override
    public void close() {
        glfwFreeCallbacks(mHandle);
        glfwDestroyWindow(mHandle);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
