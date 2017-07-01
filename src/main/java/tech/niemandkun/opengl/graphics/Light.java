package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.*;

public interface Light {
    Vector3 getDirection();
    Color getColor();
    float getAmbientIntensity();

    Matrix4 getViewMatrix();
    Matrix4 getProjectionMatrix();

    default Matrix4 getViewProjectionMatrix() {
        return getProjectionMatrix().cross(getViewMatrix());
    }
}
