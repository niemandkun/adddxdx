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
