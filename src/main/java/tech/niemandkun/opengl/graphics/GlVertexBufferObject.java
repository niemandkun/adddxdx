package tech.niemandkun.opengl.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static tech.niemandkun.opengl.graphics.VertexArray.*;

class GlVertexBufferObject implements Renderer {
    private final static int VERTEX_POSITION_ATTR_ID = 0;
    private final static int VERTEX_NORMAL_ATTR_ID = 1;
    private final static int VERTEX_COLOR_ATTR_ID = 2;
    private final static int VERTEX_UV_ATTR_ID = 3;
    private final static int SIZEOF_FLOAT = 4;

    private final static int NULL_HANDLE = -1;

    private final VertexArray mVertexArray;

    private int mObjectHandle;
    private int mBufferHandle;

    boolean isAllocated() {
        return mObjectHandle != NULL_HANDLE;
    }

    GlVertexBufferObject(VertexArray vertexArray) {
        mVertexArray = vertexArray;
        mObjectHandle = NULL_HANDLE;
        mBufferHandle = NULL_HANDLE;
    }

    void allocate() {
        if (mObjectHandle != NULL_HANDLE) throw new IllegalStateException("VBO is already allocated.");

        mObjectHandle = glGenVertexArrays();
        glBindVertexArray(mObjectHandle);

        mBufferHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mBufferHandle);
        glBufferData(GL_ARRAY_BUFFER, mVertexArray.getVertexBuffer(), GL_STATIC_DRAW);
    }

    void deallocate() {
        glDeleteVertexArrays(mObjectHandle);
        glDeleteBuffers(mBufferHandle);

        mObjectHandle = NULL_HANDLE;
        mBufferHandle = NULL_HANDLE;
    }

    @Override
    public void render(RenderTarget target, RenderSettings settings) {
        if (!isAllocated()) allocate();

        glBindVertexArray(mObjectHandle);
        glBindBuffer(GL_ARRAY_BUFFER, mBufferHandle);
        setupVertexAttributesPointers();

        glDrawArrays(GL_TRIANGLES, 0, mVertexArray.getVertexBuffer().length);
    }

    private void setupVertexAttributesPointers() {
        int stride = mVertexArray.getVertexSize() * SIZEOF_FLOAT;
        int offset = 0;

        glEnableVertexAttribArray(VERTEX_POSITION_ATTR_ID);
        glVertexAttribPointer(VERTEX_POSITION_ATTR_ID, POSITION_COMPONENTS_PER_VERTEX, GL_FLOAT, false, stride, offset);
        offset += POSITION_COMPONENTS_PER_VERTEX * SIZEOF_FLOAT;

        if (mVertexArray.hasNormalData()) {
            glEnableVertexAttribArray(VERTEX_NORMAL_ATTR_ID);
            glVertexAttribPointer(VERTEX_NORMAL_ATTR_ID, NORMAL_COMPONENTS_PER_VERTEX, GL_FLOAT, false, stride, offset);
            offset += NORMAL_COMPONENTS_PER_VERTEX * SIZEOF_FLOAT;
        }

        if (mVertexArray.hasColorData()) {
            glEnableVertexAttribArray(VERTEX_COLOR_ATTR_ID);
            glVertexAttribPointer(VERTEX_COLOR_ATTR_ID, COLOR_COMPONENTS_PER_VERTEX, GL_FLOAT, false, stride, offset);
            offset += COLOR_COMPONENTS_PER_VERTEX * SIZEOF_FLOAT;
        }

        if (mVertexArray.hasUvData()) {
            glEnableVertexAttribArray(VERTEX_UV_ATTR_ID);
            glVertexAttribPointer(VERTEX_UV_ATTR_ID, UV_COMPONENTS_PER_VERTEX, GL_FLOAT, false, stride, offset);
        }
    }
}
