package tech.niemandkun.opengl.shapes;

import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Transform;

public interface RenderTarget {
    void clear(Color color);
    void render(VertexArray vertices, Material material, Transform transform);

    default void clear() { clear(Color.BLACK); }
}
