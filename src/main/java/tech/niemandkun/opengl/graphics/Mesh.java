package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import tech.niemandkun.opengl.math.*;

public class Mesh {
    private final VertexArray mVertexArray;

    public VertexArray getVertexArray() { return mVertexArray; }

    public Mesh(@NotNull Vector3[] vertices) {
        mVertexArray = new VertexArray(vertices, null, null, null);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Color[] colors) {
        mVertexArray = new VertexArray(vertices, null, colors, null);
    }
}
