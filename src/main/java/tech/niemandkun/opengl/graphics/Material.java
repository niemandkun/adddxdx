package tech.niemandkun.opengl.graphics;

public abstract class Material {
    protected final static String UNIFORM_MVP_MATRIX = "mvpMatrix";
    protected final static String UNIFORM_MV_MATRIX = "mvMatrix";
    protected final static String UNIFORM_V_MATRIX = "vMatrix";
    protected final static String UNIFORM_LIGHT_DIRECTION = "lightDirection";
    protected final static String UNIFORM_LIGHT_MATRIX = "lightMatrix";
    protected final static String UNIFORM_SHADOW_MAP = "shadowMap";

    private Shader mShader;

    Shader getShader() {
        return mShader;
    }

    void setShader(Shader shader) {
        mShader = shader;
    }

    public final void setupShader(RenderSettings settings) {
        setupShader(settings, getShader());
    }

    public abstract void setupShader(RenderSettings settings, Shader shader);

    public abstract String getShaderName();
}
