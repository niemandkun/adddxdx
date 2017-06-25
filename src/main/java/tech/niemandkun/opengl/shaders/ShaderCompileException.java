package tech.niemandkun.opengl.shaders;

public abstract class ShaderCompileException extends Exception {
    ShaderCompileException(String message) {
        super(message);
    }
}
