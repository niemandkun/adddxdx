package tech.niemandkun.opengl.graphics.support.components;

import tech.niemandkun.opengl.graphics.Camera;
import tech.niemandkun.opengl.math.*;

import static tech.niemandkun.opengl.math.FMath.PI;

public class PerspectiveCamera extends Camera {
    private static final float DEFAULT_NEAR_PLANE = 0.3f;
    private static final float DEFAULT_FAR_PLANE = 1000f;
    private static final float DEFAULT_ASPECT_RATIO = 16 / 9f;
    private static final float DEFAULT_FIELD_OF_VIEW = PI / 3;

    private float mNearPlane;
    private float mFarPlane;
    private float mFieldOfView;
    private float mAspectRatio;

    public PerspectiveCamera() {
        mAspectRatio = DEFAULT_ASPECT_RATIO;
        mNearPlane = DEFAULT_NEAR_PLANE;
        mFarPlane = DEFAULT_FAR_PLANE;
        mFieldOfView = DEFAULT_FIELD_OF_VIEW;
    }

    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 cameraTransform = Matrix4.getRotationMatrix(0, PI, 0);
        return viewMatrix.cross(cameraTransform).inverse();
    }

    @Override
    public Matrix4 getProjectionMatrix() {
        return Projection.perspective(mFieldOfView, mAspectRatio, mNearPlane, mFarPlane);
    }

    @Override
    public void adjustAspectRatio(Size targetSize) {
        mAspectRatio = (float) targetSize.getWidth() / targetSize.getHeight();
    }

    float getAspectRatio() {
        return mAspectRatio;
    }

    float getNearPlane() {
        return mNearPlane;
    }

    void setNearPlane(float nearPlane) {
        mNearPlane = nearPlane;
    }

    float getFarPlane() {
        return mFarPlane;
    }

    void setFarPlane(float farPlane) {
        mFarPlane = farPlane;
    }

    float getFieldOfView() {
        return mFieldOfView;
    }

    void setFieldOfView(float fieldOfView) {
        mFieldOfView = fieldOfView;
    }
}
