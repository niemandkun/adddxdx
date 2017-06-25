package tech.niemandkun.opengl;

import tech.niemandkun.opengl.math.*;
import tech.niemandkun.opengl.shaders.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

class Triangles {
    private int[] mVAOs = new int[1];
    private int[] mBuffers = new int[2];

    private final static int VERTEX_BUFFER = 0;
    private final static int COLOR_BUFFER = 1;

    private final static int VERTICES_COUNT = 6;
    private final static int VERTEX_POSITION_ATTR_ID = 0;
    private final static int VERTEX_COLOR_ATTR_ID = 1;

    private final GlProgram mProgram;

    Triangles() {
        glGenVertexArrays(mVAOs);
        glBindVertexArray(mVAOs[0]);
        glEnableVertexAttribArray(VERTEX_POSITION_ATTR_ID);
        glEnableVertexAttribArray(VERTEX_COLOR_ATTR_ID);

        glGenBuffers(mBuffers);

        float[] vertices = new float[] {
                -0.90f, -0.90f,
                 0.85f, -0.90f,
                -0.90f,  0.85f,
                 0.90f, -0.85f,
                 0.90f,  0.90f,
                -0.85f,  0.90f,
        };

        glBindBuffer(GL_ARRAY_BUFFER, mBuffers[VERTEX_BUFFER]);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_POSITION_ATTR_ID, 2, GL_FLOAT, false, 0, 0);

        float[] colors = new float[] {
                0.676f,  0.977f,  0.133f,
                0.971f,  0.572f,  0.833f,
                0.140f,  0.616f,  0.489f,
                0.997f,  0.513f,  0.064f,
                0.945f,  0.719f,  0.592f,
                0.543f,  0.021f,  0.978f,
        };

        glBindBuffer(GL_ARRAY_BUFFER, mBuffers[COLOR_BUFFER]);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_COLOR_ATTR_ID, 3, GL_FLOAT, false, 0, 0);

        try {

            mProgram = new GlslCompiler()
                    .addFragmentShader("simple.frag")
                    .addVertexShader("simple.vert")
                    .compile();

        } catch (ProgramCompileException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    void render() {
        mProgram.enable();
        mProgram.setUniform("mvp", Matrix4.IDENTITY);
        glBindVertexArray(mVAOs[0]);
        glDrawArrays(GL_TRIANGLES, 0, VERTICES_COUNT);
    }
}
