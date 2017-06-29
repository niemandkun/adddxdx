package tech.niemandkun.opengl.graphics.support;

import tech.niemandkun.opengl.graphics.Light;
import tech.niemandkun.opengl.math.*;

public class DirectionalLight extends Light {
    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 rotationFix = Matrix4.getRotationMatrix(0, (float) Math.PI, 0);
        return viewMatrix.cross(rotationFix).inverse();
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        return Projection.ortho(-15, 10, -20, 20, -50, 50);
    }

    @Override
    public Vector3 getDirection() {
        return getActor().getTransform().getViewDirection();
    }
}
