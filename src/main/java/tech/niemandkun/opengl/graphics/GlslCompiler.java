package tech.niemandkun.opengl.graphics;

import java.io.File;
import java.util.*;

import static org.lwjgl.opengl.GL20.*;

class GlslCompiler implements ShaderCompiler {
    private final Map<Integer, GlShaderSource> mShaders;

    GlslCompiler() {
        mShaders = new HashMap<>();
    }

    @Override
    public GlslCompiler setFragmentShader(File shader) {
        mShaders.put(GlShaderSource.FRAGMENT, new GlShaderSource(GlShaderSource.FRAGMENT, shader));
        return this;
    }

    @Override
    public GlslCompiler setVertexShader(File shader) {
        mShaders.put(GlShaderSource.VERTEX, new GlShaderSource(GlShaderSource.VERTEX, shader));
        return this;
    }

    @Override
    public GlProgram compile() throws ShaderCompileException {
        int programHandle = glCreateProgram();

        List<GlShader> shaders = compileShaders(mShaders.values());

        shaders.forEach(s -> s.attachTo(programHandle));

        glLinkProgram(programHandle);
        checkLinkStatus(programHandle);

        shaders.forEach(s -> s.detachFrom(programHandle));
        shaders.forEach(GlShader::destroy);

        return new GlProgram(programHandle);
    }

    private static List<GlShader> compileShaders(Iterable<GlShaderSource> shaderSources)
            throws GlslCompileException {

        List<GlShader> shaders = new ArrayList<>();

        for (GlShaderSource shaderSource : shaderSources)
            shaders.add(shaderSource.compile());

        return shaders;
    }

    private static void checkLinkStatus(int programHandle) throws GlslCompileException {
        int infoLogLength = glGetProgrami(programHandle, GL_INFO_LOG_LENGTH);

        if (infoLogLength > 0) {
            String infoLog = glGetProgramInfoLog(programHandle);
            throw new GlslCompileException(infoLog);
        }
    }
}
