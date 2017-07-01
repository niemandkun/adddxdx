package tech.niemandkun.opengl.graphics.support;

import tech.niemandkun.opengl.graphics.*;

public class QuadMaterial extends Material {
    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        shader.setUniform("mtexture", settings.getShadowMapTexture());
    }

    @Override
    public ShaderDescription getShaderDescription() {
        return ShaderDescription.forFile("quad");
    }
}
