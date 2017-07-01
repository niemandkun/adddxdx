package tech.niemandkun.opengl.graphics.support.materials;

import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Matrix4;

public class DefaultMaterial extends TexturedMaterial {
    private final static String UNIFORM_MATERIAL_COLOR = "materialColor";

    private Color mColor = Color.WHITE;

    public void setColor(Color color) {
        mColor = color;
    }

    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        super.setupShader(settings, shader);

        Matrix4 modelViewMatrix = settings.getViewMatrix().cross(settings.getModelMatrix());
        Matrix4 mvpMatrix = settings.getProjectionMatrix().cross(modelViewMatrix);
        Matrix4 lightMatrix = settings.getLightMatrix().cross(settings.getModelMatrix());

        shader.setUniform(UNIFORM_V_MATRIX, settings.getViewMatrix());
        shader.setUniform(UNIFORM_MV_MATRIX, modelViewMatrix);
        shader.setUniform(UNIFORM_MVP_MATRIX, mvpMatrix);
        shader.setUniform(UNIFORM_LIGHT_MATRIX, lightMatrix);
        shader.setUniform(UNIFORM_LIGHT_DIRECTION, settings.getLightDirection());
        shader.setUniform(UNIFORM_SHADOW_MAP, settings.getShadowMapTexture());
        shader.setUniform(UNIFORM_MATERIAL_COLOR, mColor.toVector3());
    }

    @Override
    public ShaderDescription getShaderDescription() { return ShaderDescription.forFile("default"); }
}
