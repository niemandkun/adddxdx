package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.io.Size;
import tech.niemandkun.opengl.math.*;

public class Camera {
    private float mNearPlane;
    private float mFarPlane;
    private float mFieldOfView;
    private float mAspectRatio;

    Camera() {
        mAspectRatio = 16 / 9f;
        mNearPlane = 0.3f;
        mFarPlane = 1000;
        mFieldOfView = (float) Math.PI / 6;
    }

    Matrix4 getViewMatrix() {
        return Projection.perspective(mFieldOfView, mAspectRatio, mNearPlane, mFarPlane);
    }

    float getAspectRatio() {
        return mAspectRatio;
    }

    void adjustAspectRatio(Size windowSize) {
        mAspectRatio = (float) windowSize.getWidth() / windowSize.getHeight();
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
