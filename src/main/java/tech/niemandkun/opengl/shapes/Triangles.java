package tech.niemandkun.opengl.shapes;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.math.*;
import tech.niemandkun.opengl.shaders.*;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Triangles implements Destroyable {

    private final int[] mBuffers = new int[2];
    private final int mVertexArray;

    private final static int VERTEX_BUFFER = 0;
    private final static int COLOR_BUFFER = 1;

    private final static int VERTICES_COUNT = 6;
    private final static int VERTEX_POSITION_ATTR_ID = 0;
    private final static int VERTEX_COLOR_ATTR_ID = 1;

    private final Shader mShader;

    public Triangles() {
        mVertexArray = glGenVertexArrays();
        glBindVertexArray(mVertexArray);
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
                1, 0, 0,
                0, 1, 0,
                0, 0, 1,
                0, 1, 0,
                1, 0, 0,
                0, 0, 1,
        };

        glBindBuffer(GL_ARRAY_BUFFER, mBuffers[COLOR_BUFFER]);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_COLOR_ATTR_ID, 3, GL_FLOAT, false, 0, 0);

        try {

            mShader = Shader.getCompiler()
                    .setFragmentShader(open("simple.frag"))
                    .setVertexShader(open("simple.vert"))
                    .compile();

        } catch (ShaderCompileException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private File open(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }

    public void render() {
        mShader.enable();
        mShader.setUniform("mvp", Matrix4.IDENTITY);

        glBindVertexArray(mVertexArray);
        glDrawArrays(GL_TRIANGLES, 0, VERTICES_COUNT);
    }

    @Override
    public void destroy() {
        mShader.destroy();

        glDeleteBuffers(mBuffers);
        glDeleteVertexArrays(mVertexArray);
    }
}
