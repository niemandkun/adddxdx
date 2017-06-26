package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.*;

public interface Shader extends Destroyable {
    int getHandle();

    default void setUniform(String uniformName, Matrix4 matrix) {
        setUniform(uniformName, matrix, true);
    }

    void setUniform(String uniformName, Matrix4 matrix, boolean transpose);

    void setUniform(String uniformName, Vector4 vector);
    void setUniform(String uniformName, Vector3 vector);
    void setUniform(String uniformName, Vector2 vector);

    void setUniform(String uniformName, float value);
    void setUniform(String uniformName, int value);

    static ShaderCompiler getCompiler() {
        return new GlslCompiler();
    }
}
