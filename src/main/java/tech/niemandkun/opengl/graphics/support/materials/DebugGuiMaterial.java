package tech.niemandkun.opengl.graphics.support.materials;

import tech.niemandkun.opengl.graphics.*;

public class DebugGuiMaterial extends Material {
    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        shader.setUniform(UNIFORM_SHADOW_MAP, settings.getShadowMapTexture());
    }

    @Override
    public ShaderDescription getShaderDescription() { return ShaderDescription.forFile("debug"); }
}
