package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.*;

public class Camera extends GraphicsSystem.Component {
    private static final float DEFAULT_NEAR_PLANE = 0.3f;
    private static final float DEFAULT_FAR_PLANE = 1000f;
    private static final float DEFAULT_ASPECT_RATIO = 16 / 9f;
    private static final float DEFAULT_FIELD_OF_VIEW = (float) Math.PI / 2.5f;

    private float mNearPlane;
    private float mFarPlane;
    private float mFieldOfView;
    private float mAspectRatio;

    public Camera() {
        mAspectRatio = DEFAULT_ASPECT_RATIO;
        mNearPlane = DEFAULT_NEAR_PLANE;
        mFarPlane = DEFAULT_FAR_PLANE;
        mFieldOfView = DEFAULT_FIELD_OF_VIEW;
    }

    @Override
    public void connect(GraphicsSystem system) {
        if (system.getCamera() == null) system.setCamera(this);
    }

    @Override
    public void disconnect(GraphicsSystem system) {
        if (this.equals(system.getCamera())) system.setCamera(null);
    }

    Matrix4 getMvpMatrix() {
        Matrix4 projectionMatrix = Projection.perspective(mFieldOfView, mAspectRatio, mNearPlane, mFarPlane);
        return projectionMatrix.cross(getViewMatrix());
    }

    Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 cameraTransform = Matrix4.getRotationMatrix(0, (float) Math.PI, 0);
        return viewMatrix.cross(cameraTransform).inverse();
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
