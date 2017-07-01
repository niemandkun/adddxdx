package tech.niemandkun.opengl.graphics.support.components;

import tech.niemandkun.opengl.graphics.Light;
import tech.niemandkun.opengl.math.*;

import static tech.niemandkun.opengl.math.FMath.PI;

public class DirectionalLight extends Light {
    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 rotationFix = Matrix4.getRotationMatrix(0, PI, 0);
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
