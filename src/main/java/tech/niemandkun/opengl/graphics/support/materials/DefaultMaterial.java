package tech.niemandkun.opengl.graphics.support.materials;

import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Matrix4;

public class DefaultMaterial extends TexturedMaterial {
    private final static String UNIFORM_MV_MATRIX = "mvMatrix";
    private final static String UNIFORM_V_MATRIX = "vMatrix";

    private final static String UNIFORM_MAT_COLOR = "materialColor";
    private final static String UNIFORM_MAT_SPECULAR_COLOR = "materialSpecularColor";
    private final static String UNIFORM_MAT_SHININESS = "materialShininess";

    private final static String UNIFORM_LIGHT_DIRECTION = "lightDirection";
    private final static String UNIFORM_LIGHT_MATRIX = "lightMatrix";
    private final static String UNIFORM_LIGHT_COLOR = "lightColor";
    private final static String UNIFORM_AMBIENT_LIGHT = "ambientLight";

    private final static String UNIFORM_FOG_COLOR = "fogColor";
    private final static String UNIFORM_FOG_EXTINCTION = "fogExtinction";
    private final static String UNIFORM_FOG_DENSITY = "fogDensity";

    private Color mColor = Color.WHITE;
    private Color mSpecularColor = Color.BLACK;
    private float mShininess = 5;

    public void setColor(Color color) {
        mColor = color;
    }

    public void setSpecularColor(Color specularColor) {
        mSpecularColor = specularColor;
    }

    public void setShininess(float shininess) {
        mShininess = shininess;
    }

    public Color getColor() {
        return mColor;
    }

    public Color getSpecularColor() {
        return mSpecularColor;
    }

    public float getShininess() {
        return mShininess;
    }

    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        super.setupShader(settings, shader);

        Matrix4 modelViewMatrix = settings.getViewMatrix().cross(settings.getModelMatrix());
        Matrix4 mvpMatrix = settings.getProjectionMatrix().cross(modelViewMatrix);

        shader.setUniform(UNIFORM_V_MATRIX, settings.getViewMatrix());
        shader.setUniform(UNIFORM_MV_MATRIX, modelViewMatrix);
        shader.setUniform(UNIFORM_MVP_MATRIX, mvpMatrix);

        shader.setUniform(UNIFORM_MAT_COLOR, mColor.toVector3());
        shader.setUniform(UNIFORM_MAT_SHININESS, mShininess);
        shader.setUniform(UNIFORM_MAT_SPECULAR_COLOR, mSpecularColor.toVector3());

        shader.setUniform(UNIFORM_SHADOW_MAP, settings.getShadowMapTexture());

        if (settings.getLight() != null) {
            Matrix4 lightMatrix = settings.getLight().getViewProjectionMatrix().cross(settings.getModelMatrix());
            shader.setUniform(UNIFORM_LIGHT_MATRIX, lightMatrix);
            shader.setUniform(UNIFORM_LIGHT_DIRECTION, settings.getLight().getDirection());
            shader.setUniform(UNIFORM_LIGHT_COLOR, settings.getLight().getColor().toVector3());
            shader.setUniform(UNIFORM_AMBIENT_LIGHT, settings.getLight().getAmbientIntensity());
        }

        if (settings.getFog() != null) {
            shader.setUniform(UNIFORM_FOG_COLOR, settings.getFog().getColor().toVector3());
            shader.setUniform(UNIFORM_FOG_DENSITY, settings.getFog().getDensity());
            shader.setUniform(UNIFORM_FOG_EXTINCTION, settings.getFog().getExtinction());
        }
    }

    @Override
    public ShaderDescription getShaderDescription() { return ShaderDescription.forFile("default"); }
}
