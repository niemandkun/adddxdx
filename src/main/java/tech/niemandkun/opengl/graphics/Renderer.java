package tech.niemandkun.opengl.graphics;

public interface Renderer {
    void render(VertexArray vertices, RenderSettings settings);

    default void render(Renderable renderable, RenderSettings settings) {
        renderable.render(this, settings);
    }
}
