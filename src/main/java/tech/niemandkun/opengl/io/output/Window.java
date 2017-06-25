package tech.niemandkun.opengl.io.output;

import org.lwjgl.glfw.GLFWKeyCallbackI;
import tech.niemandkun.opengl.infrastructure.Destroyable;

public interface Window extends Destroyable {
    void clear();
    void display();
    boolean isOpen();

    // FIXME
    void setKeyCallback(GLFWKeyCallbackI callback);

    static WindowBuilder builder() {
        return new GlfwWindowBuilder();
    }
}
