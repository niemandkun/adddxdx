package tech.niemandkun.opengl.shaders;

class ShaderCompileException extends Exception {
    ShaderCompileException(String filename, String compileLog) {
        super("Cannot compile shader '" + filename + "', see log for details:\n" + compileLog);
    }
}
