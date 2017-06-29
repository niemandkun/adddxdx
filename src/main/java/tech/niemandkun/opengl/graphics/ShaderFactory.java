package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;

import java.io.File;

public class ShaderFactory implements Destroyable {
    private final static String DEFAULT_SHADER_NAME = "cartoon";
    private final static String FRAGMENT_SHADER_EXT = ".frag";
    private final static String VERTEX_SHADER_EXT = ".vert";

    private final Shader mDefaultShader;
    private final Material mDefaultMaterial;

    public ShaderFactory() {
        mDefaultShader = buildShader(DEFAULT_SHADER_NAME);
        mDefaultMaterial = new Material(mDefaultShader);
    }

    Shader buildShader(String shaderName) {
        try {
            return Shader.getCompiler()
                    .setFragmentShader(open(shaderName + FRAGMENT_SHADER_EXT))
                    .setVertexShader(open(shaderName + VERTEX_SHADER_EXT))
                    .compile();
        } catch (ShaderCompileException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private File open(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }

    public Material getDefaultMaterial() {
        return mDefaultMaterial;
    }

    @Override
    public void destroy() {
        mDefaultShader.destroy();
    }
}
