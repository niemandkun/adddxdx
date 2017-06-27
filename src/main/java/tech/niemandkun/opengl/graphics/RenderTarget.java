package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.components.Camera;
import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Transform;

public interface RenderTarget extends Destroyable {
    void init();
    void clear(Color color);
    void render(VertexBufferObject vertices, Material material, Transform transform);

    void setCamera(Camera camera);
    Camera getCamera();

    default void clear() { clear(Color.BLACK); }
}
