package tech.niemandkun.opengl.shapes;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;

public class Mesh implements Destroyable {
    private VertexArray mVertexArray;

    VertexArray getVertexArray() { return mVertexArray; }

    public Mesh(Vector3[] vertices) {
        mVertexArray = new VertexArray(vertices, null, null, null);
    }

    public Mesh(Vector3[] vertices, Color[] colors) {
        mVertexArray = new VertexArray(vertices, null, colors, null);
    }

    @Override
    public void destroy() {
        if (mVertexArray.isAllocated())
            mVertexArray.deallocateVertexBufferObject();
    }
}
