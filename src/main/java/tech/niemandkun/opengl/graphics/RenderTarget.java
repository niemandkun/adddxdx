package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Size;

public interface RenderTarget extends Destroyable {
    void init();
    void enable();
    Size getSize();
    void clear(Color color);
    default void clear() { clear(Color.BLACK); }
}
