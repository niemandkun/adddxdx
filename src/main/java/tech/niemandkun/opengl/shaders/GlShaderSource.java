package tech.niemandkun.opengl.shaders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

class GlShaderSource {
    final static int FRAGMENT = GL_FRAGMENT_SHADER;
    final static int VERTEX = GL_VERTEX_SHADER;

    private final File mFile;
    private final int mType;

    GlShaderSource(int type, File file) {
        mType = type;
        mFile = file;
    }

    GlShader compile() throws GlslCompileException {
        int shaderHandle = glCreateShader(mType);
        glShaderSource(shaderHandle, readSourceCode(mFile));
        glCompileShader(shaderHandle);
        checkCompileStatus(shaderHandle, mFile);
        return new GlShader(shaderHandle);
    }

    private static void checkCompileStatus(int shaderHandle, File file) throws GlslCompileException {
        int infoLogLength = glGetShaderi(shaderHandle, GL_INFO_LOG_LENGTH);

        if (infoLogLength > 0) {
            String infoLog = glGetShaderInfoLog(shaderHandle);
            throw new GlslCompileException(file.getName(), infoLog);
        }
    }

    private static String readSourceCode(File file) {
        String shaderSourceCode;

        try {
            shaderSourceCode = readTextFile(file);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Cannot load shaders: " + exception);
        }

        return shaderSourceCode;
    }

    private static String readTextFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        StringBuilder text = new StringBuilder();

        for (String line : lines) text.append(line).append("\n");

        return text.toString();
    }
}
