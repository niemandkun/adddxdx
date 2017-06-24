package tech.niemandkun.opengl.shaders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

public class GlslCompiler {
    private final List<GlShaderSource> mShaders;

    public GlslCompiler() {
        mShaders = new ArrayList<>();
    }

    public GlslCompiler addFragmentShader(String shader) {
        return addFragmentShader(getFile(shader));
    }

    public GlslCompiler addFragmentShader(File shader) {
        mShaders.add(new GlShaderSource(GlShaderSource.FRAGMENT, shader));
        return this;
    }

    public GlslCompiler addVertexShader(String shader) {
        return addVertexShader(getFile(shader));
    }

    public GlslCompiler addVertexShader(File shader) {
        mShaders.add(new GlShaderSource(GlShaderSource.VERTEX, shader));
        return this;
    }

    private static File getFile(String resource) {
        return new File(GlslCompiler.class.getClassLoader().getResource(resource).getFile());
    }

    public GlProgram compile() throws ProgramCompileException {
        int programHandle = glCreateProgram();

        List<GlShader> shaders = compileShaders(mShaders);

        shaders.forEach(s -> s.attachTo(programHandle));

        glLinkProgram(programHandle);
        checkLinkStatus(programHandle);

        shaders.forEach(s -> s.detachFrom(programHandle));
        shaders.forEach(GlShader::unload);

        return new GlProgram(programHandle);
    }

    private static List<GlShader> compileShaders(List<GlShaderSource> shaderSources) throws ProgramCompileException {
        List<GlShader> shaders = new ArrayList<>();

        try {
            for (GlShaderSource shaderSource : shaderSources)
                shaders.add(shaderSource.compile());
        } catch (ShaderCompileException e) {
            throw new ProgramCompileException(e);
        }

        return shaders;
    }

    private static void checkLinkStatus(int programHandle) throws ProgramCompileException {
        int infoLogLength = glGetProgrami(programHandle, GL_INFO_LOG_LENGTH);

        if (infoLogLength > 0) {
            String infoLog = glGetProgramInfoLog(programHandle);
            throw new ProgramCompileException(infoLog);
        }
    }
}
