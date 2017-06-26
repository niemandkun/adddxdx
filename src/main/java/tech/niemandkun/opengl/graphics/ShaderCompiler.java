package tech.niemandkun.opengl.graphics;

import java.io.File;

public interface ShaderCompiler {
    ShaderCompiler setFragmentShader(File shader);
    ShaderCompiler setVertexShader(File shader);

    Shader compile() throws ShaderCompileException;
}
