package tech.niemandkun.opengl.graphics.support.materials;

import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.math.Matrix4;

public class SkyMaterial extends Material {
    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        Matrix4 m = settings.getProjectionMatrix().cross(settings.getViewMatrix()).inverse();
        shader.setUniform("inverseProjectionViewMatrix", m);
    }

    @Override
    public ShaderDescription getShaderDescription() { return ShaderDescription.forFile("sky"); }
}
