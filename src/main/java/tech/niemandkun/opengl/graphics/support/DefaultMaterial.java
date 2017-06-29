package tech.niemandkun.opengl.graphics.support;

import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.math.Matrix4;

public class DefaultMaterial extends Material {
    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        Matrix4 modelViewMatrix = settings.getViewMatrix().cross(settings.getModelMatrix());
        Matrix4 mvpMatrix = settings.getProjectionMatrix().cross(modelViewMatrix);
        Matrix4 lightMatrix = settings.getLightMatrix().cross(settings.getModelMatrix());

        shader.setUniform(UNIFORM_V_MATRIX, settings.getViewMatrix());
        shader.setUniform(UNIFORM_MV_MATRIX, modelViewMatrix);
        shader.setUniform(UNIFORM_MVP_MATRIX, mvpMatrix);
        shader.setUniform(UNIFORM_LIGHT_MATRIX, lightMatrix);
        shader.setUniform(UNIFORM_LIGHT_DIRECTION, settings.getLightDirection());
        shader.setUniform(UNIFORM_SHADOW_MAP, settings.getShadowMapTexture());
    }

    @Override
    public String getShaderName() { return "default"; }
}
