package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.engine.Destroyable;

import java.io.File;

public class ShaderFactory implements Destroyable {
    private final Shader mDefaultShader;
    private final Material mDefaultMaterial;

    public ShaderFactory() {
        mDefaultShader = buildShader("simple");
        mDefaultMaterial = new Material(mDefaultShader);
    }

    Shader buildShader(String shaderName) {
        try {
            return Shader.getCompiler()
                    .setFragmentShader(open(shaderName + ".frag"))
                    .setVertexShader(open(shaderName + ".vert"))
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
