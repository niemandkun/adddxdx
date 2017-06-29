package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MaterialFactory implements Destroyable {
    private final static String FRAGMENT_SHADER_EXT = ".frag";
    private final static String VERTEX_SHADER_EXT = ".vert";

    private final ShaderCompiler mShaderCompiler;

    private final Map<Class<? extends Material>, Material> mMaterials;

    public MaterialFactory() {
        mShaderCompiler = Shader.getCompiler();
        mMaterials = new HashMap<>();
    }

    public Material get(Class<? extends Material> material) {
        Material instance = mMaterials.get(material);

        if (instance == null) {
            instance = instantiateMaterial(material);
            instance.setShader(buildShader(instance.getShaderDescription()));
            mMaterials.put(material, instance);
        }

        return instance;
    }

    private Material instantiateMaterial(Class<? extends Material> material) {
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
        for (Material material : mMaterials.values())
            material.getShader().destroy();
    }
}
