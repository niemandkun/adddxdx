package tech.niemandkun.opengl.shapes;

import tech.niemandkun.opengl.math.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class GlRenderTarget implements RenderTarget {
    @Override
    public void clear(Color color) {
        Vector4 clearColor = color.toVector4();
        glClearColor(clearColor.getX(), clearColor.getY(), clearColor.getZ(), clearColor.getW());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void render(VertexArray vertices, Material material, Transform transform) {
        if (!vertices.isAllocated())
            vertices.allocateVertexBufferObject();

        glBindVertexArray(vertices.getHandle());
        glUseProgram(material.getShader().getHandle());
        glDrawArrays(GL_TRIANGLES, 0, vertices.getVertices().length);
    }
}
