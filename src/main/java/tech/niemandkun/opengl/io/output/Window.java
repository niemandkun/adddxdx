package tech.niemandkun.opengl.io.output;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.io.input.EventQueueKeyboard;

public interface Window extends Destroyable {
    void clear();
    void display();
    boolean isOpen();

    EventQueueKeyboard getKeyboard();

    static WindowBuilder builder() {
        return new GlfwWindowBuilder();
    }
}
