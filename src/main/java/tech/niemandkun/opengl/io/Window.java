package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.io.builders.WindowBuilder;

import java.io.Closeable;

public interface Window extends Closeable {
    void clear();
    void display();
    boolean isOpen();
    void close();

    static WindowBuilder builder() {
        return new WindowBuilder();
    }
}
