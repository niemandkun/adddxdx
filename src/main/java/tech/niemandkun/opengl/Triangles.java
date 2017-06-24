package tech.niemandkun.opengl;

import tech.niemandkun.opengl.shaders.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

class Triangles {
    private int[] mVAOs = new int[1];
    private int[] mBuffers = new int[1];

    private final static int VERTICES_COUNT = 6;
    private final static int VERTICES_ATTR_ID = 0;

    Triangles() {
        glGenVertexArrays(mVAOs);
        glBindVertexArray(mVAOs[0]);

        float[] vertices = new float[] {
                -0.90f, -0.90f,
                 0.85f, -0.90f,
                -0.90f,  0.85f,
                 0.90f, -0.85f,
                 0.90f,  0.90f,
                -0.85f,  0.90f,
        };

        glGenBuffers(mBuffers);
        glBindBuffer(GL_ARRAY_BUFFER, mBuffers[0]);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        try {

            GlProgram program = new GlslCompiler()
                    .addFragmentShader("simple.frag")
                    .addVertexShader("simple.vert")
                    .compile();

            program.enable();

        } catch (ProgramCompileException e) {
            System.out.println(e.getMessage());
        }

        glVertexAttribPointer(VERTICES_ATTR_ID, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(VERTICES_ATTR_ID);
    }

    void render() {
        glBindVertexArray(mVAOs[0]);
        glDrawArrays(GL_TRIANGLES, 0, VERTICES_COUNT);
    }
}
