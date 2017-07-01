package tech.niemandkun.opengl.graphics;

public abstract class TexturedMaterial extends Material {
    private final static String UNIFORM_TEXTURE = "mainTexture";
    private final static int TEXTURE_UNIT_TO_USE = 2;

    private GlTexture mTexture;

    public TexturedMaterial() {
        mTexture = new StubTexture();
    }

    public void setTexture(GlTexture texture) {
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
