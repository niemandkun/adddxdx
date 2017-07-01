package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.graphics.support.textures.Texture;

public abstract class TexturedMaterial extends Material {
    private final static String UNIFORM_TEXTURE = "texture";
    private final static int TEXTURE_UNIT_TO_USE = 2;

    private GlTexture mTexture;

    public void setTexture(Texture texture) {
        mTexture = texture;
    }

    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        if (mTexture != null) {
            if (!mTexture.isInitialized()) mTexture.init();
            shader.setUniform(UNIFORM_TEXTURE, mTexture.bind(TEXTURE_UNIT_TO_USE));
        }
    }
}
