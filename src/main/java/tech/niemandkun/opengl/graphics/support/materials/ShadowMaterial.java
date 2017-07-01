package tech.niemandkun.opengl.graphics.support.materials;

import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.math.Matrix4;

public class ShadowMaterial extends Material {
    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        Matrix4 mvpMatrix = settings.getProjectionMatrix()
                .cross(settings.getViewMatrix())
                .cross(settings.getModelMatrix());

        shader.setUniform(UNIFORM_MVP_MATRIX, mvpMatrix);
    }

    @Override
    public ShaderDescription getShaderDescription() { return ShaderDescription.forFile("shadow"); }
}
