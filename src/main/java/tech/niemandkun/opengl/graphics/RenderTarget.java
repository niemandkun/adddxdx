package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Transform;

public interface RenderTarget {
    void clear(Color color);
    void render(VertexArray vertices, Material material, Transform transform);

    void setCamera(Camera camera);
    Camera getCamera();

    default void clear() { clear(Color.BLACK); }
}
