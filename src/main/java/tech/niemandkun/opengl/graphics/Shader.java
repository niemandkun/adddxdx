package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.*;

public interface Shader extends Destroyable {
    int getHandle();

    void setUniform(String uniformName, Matrix4 matrix);

    void setUniform(String uniformName, Vector4 vector);
    void setUniform(String uniformName, Vector3 vector);
    void setUniform(String uniformName, Vector2 vector);

    void setUniform(String uniformName, float value);
    void setUniform(String uniformName, int value);

    static ShaderCompiler getCompiler() {
        return new GlslCompiler();
    }
}
