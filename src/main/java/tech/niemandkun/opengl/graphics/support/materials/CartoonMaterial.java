package tech.niemandkun.opengl.graphics.support.materials;

import tech.niemandkun.opengl.graphics.ShaderDescription;

public class CartoonMaterial extends DefaultMaterial {
    @Override
    public ShaderDescription getShaderDescription() {
        return ShaderDescription.forFiles("default", "cartoon");
    }
}