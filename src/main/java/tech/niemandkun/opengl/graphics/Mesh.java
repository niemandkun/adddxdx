package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;
import tech.niemandkun.opengl.math.Vector2;
import tech.niemandkun.opengl.math.Vector3;

public class Mesh {
    private final VertexArray mVertexArray;

    public VertexArray getVertexArray() { return mVertexArray; }

    public Mesh(@NotNull Vector3[] vertices) {
        mVertexArray = new VertexArray(vertices, null, null);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Vector3[] normals) {
        mVertexArray = new VertexArray(vertices, normals, null);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Vector2[] uvCoordinates) {
        mVertexArray = new VertexArray(vertices, null, uvCoordinates);
    }

    public Mesh(@NotNull Vector3[] vertices, @NotNull Vector3[] normals, @NotNull Vector2[] uvCoordinates) {
        mVertexArray = new VertexArray(vertices, normals, uvCoordinates);
    }
}
