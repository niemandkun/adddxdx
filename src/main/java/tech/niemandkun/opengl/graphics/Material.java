package tech.niemandkun.opengl.graphics;

public class Material {
    private final Shader mShader;
    Shader getShader() { return mShader; }

    Material(Shader shader) {
        mShader = shader;
    }
}
