package tech.niemandkun.opengl.shaders;

public class ProgramCompileException extends Exception {
    ProgramCompileException(String infoLog) {
        super("Cannot compile program, " + infoLog);
    }

    ProgramCompileException(ShaderCompileException shaderCompileException) {
        super(shaderCompileException.getMessage());
    }
}
