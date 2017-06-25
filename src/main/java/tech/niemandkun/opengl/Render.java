package tech.niemandkun.opengl;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

class Render {
    private static final int TARGET_CONTEXT_VERSION_MAJOR = 3;
    private static final int TARGET_CONTEXT_VERSION_MINOR = 3;

    private long mWindowHandle;

    void run() {
        System.out.println("Running LWJGL " + Version.getVersion());

        init();
        loop();
        stop();
    }

    private void stop() {
        glfwFreeCallbacks(mWindowHandle);
        glfwDestroyWindow(mWindowHandle);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, TARGET_CONTEXT_VERSION_MAJOR);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, TARGET_CONTEXT_VERSION_MINOR);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        System.out.println("OpenGL version: " + glfwGetVersionString());

        mWindowHandle = glfwCreateWindow(600, 600, "Hello World!", NULL, NULL);

        if (mWindowHandle == NULL) throw new RuntimeException("Failed to create the GLFW mWindowHandle");

        glfwSetKeyCallback(mWindowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer widthPtr = stack.mallocInt(1);
            IntBuffer heightPtr = stack.mallocInt(1);

            glfwGetWindowSize(mWindowHandle, widthPtr, heightPtr);
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    mWindowHandle,
                    (videoMode.width() - widthPtr.get(0)) / 2,
                    (videoMode.height() - heightPtr.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(mWindowHandle);
        glfwSwapInterval(1);
        glfwShowWindow(mWindowHandle);
    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(0, 0, 0, 0);

        Triangles triangles = new Triangles();

        while (!glfwWindowShouldClose(mWindowHandle)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            triangles.render();

            glfwSwapBuffers(mWindowHandle);
            glfwPollEvents();
        }
    }
}
