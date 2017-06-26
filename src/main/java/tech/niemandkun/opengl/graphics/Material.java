package tech.niemandkun.opengl.graphics;

class Material {
    private final Shader mShader;
    Shader getShader() { return mShader; }

    Material(Shader shader) {
        mShader = shader;
    }
}
