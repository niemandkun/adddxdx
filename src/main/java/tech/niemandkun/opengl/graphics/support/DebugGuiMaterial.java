package tech.niemandkun.opengl.graphics.support;

import tech.niemandkun.opengl.graphics.*;

public class DebugGuiMaterial extends Material {
    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        shader.setUniform(UNIFORM_SHADOW_MAP, settings.getShadowMapTexture());
    }

    @Override
    public String getShaderName() { return "debug"; }
}