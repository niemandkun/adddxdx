package tech.niemandkun.opengl.shapes;

import tech.niemandkun.opengl.shaders.Shader;

class Material {
    private final Shader mShader;
    Shader getShader() { return mShader; }

    Material(Shader shader) {
        mShader = shader;
    }
}
