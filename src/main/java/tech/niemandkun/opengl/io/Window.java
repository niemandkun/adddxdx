package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.Size;

public interface Window extends Destroyable {
    void update();
    boolean isOpen();
    Size getSize();

    EventQueueKeyboard getKeyboard();

    static WindowBuilder builder() {
        return new GlfwWindowBuilder();
    }
}
