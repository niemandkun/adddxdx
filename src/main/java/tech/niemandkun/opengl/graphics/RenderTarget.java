package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.Color;

public interface RenderTarget extends Destroyable {
    void init();
    void clear(Color color);
    default void clear() { clear(Color.BLACK); }
}
