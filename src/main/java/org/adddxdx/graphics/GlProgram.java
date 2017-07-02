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

import org.adddxdx.math.*;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

class GlProgram implements Shader {
    private final int mHandle;
    private final Map<String, Integer> mUniformLocations;

    GlProgram(int handle) {
        mHandle = handle;
        mUniformLocations = new HashMap<>();
    }

    @Override
    public void enable() {
        glUseProgram(mHandle);
    }

    @Override
    public void destroy() {
        glDeleteProgram(mHandle);
    }

    @Override
    public void setUniform(String uniformName, Matrix4 matrix) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniformMatrix4fv(uniformLocation, true, matrix.toFloatArray());
    }

    @Override
    public void setUniform(String uniformName, Vector4 vector) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform4fv(uniformLocation, vector.toFloatArray());
    }

    @Override
    public void setUniform(String uniformName, Vector3 vector) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform3fv(uniformLocation, vector.toFloatArray());
    }

    @Override
    public void setUniform(String uniformName, Vector2 vector) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform2fv(uniformLocation, vector.toFloatArray());
    }

    @Override
    public void setUniform(String uniformName, float value) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform1f(uniformLocation, value);
    }

    @Override
    public void setUniform(String uniformName, int value) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform1i(uniformLocation, value);
    }

    private int getUniformLocation(String uniformName) {
        if (mUniformLocations.containsKey(uniformName))
            return mUniformLocations.get(uniformName);

        int uniformLocation = getUniformLocationOpenGl(uniformName);
        mUniformLocations.put(uniformName, uniformLocation);
        return uniformLocation;
    }

    private int getUniformLocationOpenGl(String uniformName) {
        int uniformLocation = glGetUniformLocation(mHandle, uniformName);

        if (uniformLocation == -1)
            throw new IllegalArgumentException("Uniform parameter with name '" + uniformName + "' is not found.");

        return uniformLocation;
    }
}
