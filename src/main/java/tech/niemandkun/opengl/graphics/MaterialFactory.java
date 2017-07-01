package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MaterialFactory implements Destroyable {
    private final static String FRAGMENT_SHADER_EXT = ".frag";
    private final static String VERTEX_SHADER_EXT = ".vert";

    private final ShaderCompiler mShaderCompiler;

    private final Map<ShaderDescription, Shader> mShaders;

    public MaterialFactory() {
        mShaderCompiler = Shader.getCompiler();
        mShaders = new HashMap<>();
    }

    public <TMaterial extends Material> TMaterial get(Class<TMaterial> material) {
        TMaterial instance = instantiateMaterial(material);
        instance.setShader(getShader(instance.getShaderDescription()));
        return instance;
    }

    Shader getShader(ShaderDescription description) {
        Shader shader = mShaders.get(description);

        if (shader == null) {
            shader = buildShader(description);
            mShaders.put(description, shader);
        }

        return shader;
    }

    private <TMaterial extends Material> TMaterial instantiateMaterial(Class<TMaterial> material) {
        try {
            return material.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate material: " + material.getClass() + ", " + e.getMessage());
        }
    }

    private Shader buildShader(ShaderDescription shaderDescription) {
        try {

            return mShaderCompiler
                    .setFragmentShader(open(shaderDescription.getFragmentShaderName() + FRAGMENT_SHADER_EXT))
                    .setVertexShader(open(shaderDescription.getVertexShaderName() + VERTEX_SHADER_EXT))
                    .compile();

        } catch (ShaderCompileException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private File open(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }

    @Override
    public void destroy() {
        mShaders.values().forEach(Shader::destroy);
    }
}
