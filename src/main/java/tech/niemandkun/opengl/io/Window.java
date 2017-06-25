package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.io.builders.GlfwWindowBuilder;
import tech.niemandkun.opengl.io.builders.WindowBuilder;

public interface Window extends Destroyable {
    void clear();
    void display();
    boolean isOpen();

    static WindowBuilder builder() {
        return new GlfwWindowBuilder();
    }
}
