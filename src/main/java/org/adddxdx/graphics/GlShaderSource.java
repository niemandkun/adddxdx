/*
 * Copyright (C) 2017 Poroshin Ivan
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.graphics;

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
