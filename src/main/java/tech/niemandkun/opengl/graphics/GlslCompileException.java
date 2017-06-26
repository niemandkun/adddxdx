package tech.niemandkun.opengl.graphics;

class GlslCompileException extends ShaderCompileException {
    GlslCompileException(String filename, String compileInfoLog) {
        super("Cannot compile shader '" + filename + "', " + compileInfoLog);
    }

    GlslCompileException(String linkingInfoLog) {
        super("Cannot link shader, " + linkingInfoLog);
    }
}
