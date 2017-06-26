package tech.niemandkun.opengl.shapes;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.shaders.Shader;
import tech.niemandkun.opengl.shaders.ShaderCompileException;

import java.io.File;

public class MaterialFactory implements Destroyable {
    private final Shader mDefaultShader;
    private final Material mDefaultMaterial;

    public MaterialFactory() {
        try {
            mDefaultShader = Shader.getCompiler()
                            .setFragmentShader(open("simple.frag"))
                            .setVertexShader(open("simple.vert"))
                            .compile();
        } catch (ShaderCompileException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }

        mDefaultMaterial = new Material(mDefaultShader);
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
