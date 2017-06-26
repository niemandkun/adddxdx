package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.engine.Component;
import tech.niemandkun.opengl.math.*;

public class Camera extends Component {
    private float mNearPlane;
    private float mFarPlane;
    private float mFieldOfView;
    private float mAspectRatio;

    public Camera() {
        mAspectRatio = 16 / 9f;
        mNearPlane = 0.3f;
        mFarPlane = 1000;
        mFieldOfView = (float) Math.PI / 6;
    }

    @Override
    protected void onCreate() {
        if (getScene().getRenderTarget().getCamera() == null)
            getScene().getRenderTarget().setCamera(this);
    }

    @Override
    protected void onDestroy() {
        if (getScene().getRenderTarget().getCamera() == this)
            getScene().getRenderTarget().setCamera(null);
    }

    public Matrix4 getMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix().inverse();
        Matrix4 projectionMatrix = Projection.perspective(mFieldOfView, mAspectRatio, mNearPlane, mFarPlane);
        return projectionMatrix.cross(viewMatrix);
    }

    float getAspectRatio() {
        return mAspectRatio;
    }

    public void adjustAspectRatio(Size windowSize) {
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
