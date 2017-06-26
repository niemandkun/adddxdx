package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import tech.niemandkun.opengl.math.*;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

class VertexArray {
    private final static int VERTEX_POSITION_ATTR_ID = 0;
    private final static int VERTEX_NORMAL_ATTR_ID = 1;
    private final static int VERTEX_COLOR_ATTR_ID = 2;
    private final static int VERTEX_UV_ATTR_ID = 3;

    private final static int COLOR_COMPONENTS_PER_VERTEX = 3;
    private final static int POSITION_COMPONENTS_PER_VERTEX = 3;
    private final static int NORMAL_COMPONENTS_PER_VERTEX = 3;
    private final static int UV_COMPONENTS_PER_VERTEX = 2;

    private final static int NULL_HANDLE = -1;

    private int mHandle;
    int getHandle() { return mHandle; }

    private final Vector3[] mVertices;
    private int mVerticesBufferHandle;
    Vector3[] getVertices() { return mVertices; }

    private final Vector3[] mNormals;
    private int mNormalsBufferHandle;
    Vector3[] getNormals() { return mNormals; }

    private final Color[] mColors;
    private int mColorsBufferHandle;
    Color[] getColors() { return mColors; }

    private final Vector2[] mUvCoordinates;
    private int mUvBufferHandle;
    Vector2[] getUvCoordinates() { return mUvCoordinates; }

    VertexArray(@NotNull Vector3[] vertices,
                @Nullable Vector3[] normals,
                @Nullable Color[] colors,
                @Nullable Vector2[] uvCoordinates) {

        mHandle = NULL_HANDLE;
        mVertices = vertices;
        mNormals = normals;
        mColors = colors;
        mUvCoordinates = uvCoordinates;
    }

    void allocateVertexBufferObject() {
        if (mHandle != NULL_HANDLE) throw new IllegalStateException("VBO is already allocated.");

        mHandle = glGenVertexArrays();
        glBindVertexArray(mHandle);
        glEnableVertexAttribArray(VERTEX_POSITION_ATTR_ID);

        mVerticesBufferHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, mVerticesBufferHandle);
        glBufferData(GL_ARRAY_BUFFER, toFloatArray(mVertices, POSITION_COMPONENTS_PER_VERTEX), GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_POSITION_ATTR_ID, POSITION_COMPONENTS_PER_VERTEX, GL_FLOAT, false, 0, 0);

        if (mNormals != null) {
            mNormalsBufferHandle = glGenBuffers();
            glEnableVertexAttribArray(VERTEX_NORMAL_ATTR_ID);
            glBindBuffer(GL_ARRAY_BUFFER, mNormalsBufferHandle);
            glBufferData(GL_ARRAY_BUFFER, toFloatArray(mNormals, NORMAL_COMPONENTS_PER_VERTEX), GL_STATIC_DRAW);
            glVertexAttribPointer(VERTEX_NORMAL_ATTR_ID, NORMAL_COMPONENTS_PER_VERTEX, GL_FLOAT, false, 0, 0);
        } else {
            mNormalsBufferHandle = NULL_HANDLE;
        }

        if (mColors != null) {
            mColorsBufferHandle = glGenBuffers();
            glEnableVertexAttribArray(VERTEX_COLOR_ATTR_ID);
            glBindBuffer(GL_ARRAY_BUFFER, mColorsBufferHandle);
            glBufferData(GL_ARRAY_BUFFER, toFloatArray(mColors, COLOR_COMPONENTS_PER_VERTEX), GL_STATIC_DRAW);
            glVertexAttribPointer(VERTEX_COLOR_ATTR_ID, COLOR_COMPONENTS_PER_VERTEX, GL_FLOAT, false, 0, 0);
        } else {
            mColorsBufferHandle = NULL_HANDLE;
        }

        if (mUvCoordinates != null) {
            mUvBufferHandle = glGenBuffers();
            glEnableVertexAttribArray(VERTEX_UV_ATTR_ID);
            glBindBuffer(GL_ARRAY_BUFFER, mUvBufferHandle);
            glBufferData(GL_ARRAY_BUFFER, toFloatArray(mUvCoordinates, UV_COMPONENTS_PER_VERTEX), GL_STATIC_DRAW);
            glVertexAttribPointer(VERTEX_UV_ATTR_ID, UV_COMPONENTS_PER_VERTEX, GL_FLOAT, false, 0, 0);
        } else {
            mUvBufferHandle = NULL_HANDLE;
        }
    }

    private static float[] toFloatArray(Vector[] vectors, int vectorLength) {
        float[] positions = new float[vectors.length * vectorLength];

        int positionArrayOffset = 0;

        for (Vector vector : vectors) {
            float[] vectorAsFloatArray = vector.toFloatArray();

            for (int j = 0; j < vectorLength; ++j)
                positions[positionArrayOffset++] = vectorAsFloatArray[j];
        }

        return positions;
    }

    private static float[] toFloatArray(Color[] colors, int componentsPerColor) {
        Vector3[] colorsAsVectors = Arrays.stream(colors).map(Color::toVector3).toArray(Vector3[]::new);
        return toFloatArray(colorsAsVectors, componentsPerColor);
    }

    boolean isAllocated() {
        return mHandle != -1;
    }

    void deallocateVertexBufferObject() {
        if (mVerticesBufferHandle != NULL_HANDLE) glDeleteBuffers(mVerticesBufferHandle);
        if (mNormalsBufferHandle != NULL_HANDLE) glDeleteBuffers(mNormalsBufferHandle);
        if (mColorsBufferHandle != NULL_HANDLE) glDeleteBuffers(mColorsBufferHandle);
        if (mUvBufferHandle != NULL_HANDLE) glDeleteBuffers(mUvBufferHandle);

        glDeleteVertexArrays(mHandle);
    }
}
