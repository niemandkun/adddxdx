package tech.niemandkun.opengl.shaders;

import tech.niemandkun.opengl.math.*;

import java.util.*;

import static org.lwjgl.opengl.GL20.*;

public class GlProgram {
    private final int mHandle;
    private final Map<String, Integer> mUniformLocations;

    GlProgram(int handle) {
        mHandle = handle;
        mUniformLocations = new HashMap<>();
    }

    public void enable() {
        glUseProgram(mHandle);
    }

    public void setUniform(String uniformName, Matrix4 matrix) {
        setUniform(uniformName, matrix, false);
    }

    public void setUniform(String uniformName, Matrix4 matrix, boolean transpose) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniformMatrix4fv(uniformLocation, transpose, matrix.toFloatArray());
    }

    public void setUniform(String uniformName, Vector4 vector) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform4fv(uniformLocation, vector.toFloatArray());
    }

    public void setUniform(String uniformName, Vector3 vector) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform3fv(uniformLocation, vector.toFloatArray());
    }

    public void setUniform(String uniformName, Vector2 vector) {
        int uniformLocation = getUniformLocation(uniformName);
        glUniform2fv(uniformLocation, vector.toFloatArray());
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
