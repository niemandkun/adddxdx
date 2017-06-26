package tech.niemandkun.opengl.shapes;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.io.input.*;
import tech.niemandkun.opengl.math.Matrix4;
import tech.niemandkun.opengl.math.Projection;
import tech.niemandkun.opengl.shaders.Shader;
import tech.niemandkun.opengl.shaders.ShaderCompileException;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Triangles implements Destroyable, Keyboard.KeyPressListener {

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

    private int cameraX = 0;
    private int cameraZ = 0;

    private float cameraRot = 0;

    @Override
    public void onKeyPressed(Keyboard keyboard, KeyboardEvent event) {
        int key = event.getKey();

             if (key == Key.W) cameraZ += 1;
        else if (key == Key.S) cameraZ -= 1;
        else if (key == Key.A) cameraX += 1;
        else if (key == Key.D) cameraX -= 1;
        else if (key == Key.Q) cameraRot += 0.1;
        else if (key == Key.E) cameraRot -= 0.1;
    }

    public void render() {
        mShader.enable();

        Matrix4 model = Matrix4.IDENTITY;

        Matrix4 view = Matrix4.getRotationMatrix(0, cameraRot, 0)
                .cross(Matrix4.getTranslationMatrix(cameraX, 0, cameraZ));

        Matrix4 perspective = Projection.perspective((float) Math.PI / 3, 4/3f, 0.3f, 100);

        mShader.setUniform("mvp", perspective.cross(view).cross(model));

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
