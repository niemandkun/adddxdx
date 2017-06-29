package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;

public class Mesh {
    private final VertexArray mVertexArray;

    public VertexArray getVertexArray() { return mVertexArray; }

    public Mesh(@NotNull Vector3[] vertices) {
        mVertexArray = new VertexArray(vertices, null, null, null);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Color[] colors) {
        mVertexArray = new VertexArray(vertices, null, colors, null);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Vector3[] normals, @NotNull Color[] colors) {
        mVertexArray = new VertexArray(vertices, normals, colors, null);
    }
}
