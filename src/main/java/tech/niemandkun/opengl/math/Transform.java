package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Transform {
    private Vector3 mLocation;
    private Vector3 mScale;
    private Quaternion mRotation;

    public Transform() {
        mLocation = Vector3.ZERO;
        mScale = Vector3.ONE;
        mRotation = Quaternion.IDENTITY;
    }

    public void translate(float x, float y, float z) {
        translate(new Vector3(x, y, z));
    }

    public void translate(@NotNull Vector3 direction) {
        mLocation = mLocation.add(direction);
    }

    public void scale(float x, float y, float z) {
        scale(new Vector3(x, y, z));
    }

    public void scale(@NotNull Vector3 v) {
        mScale = new Vector3(
                mScale.getX() * v.getX(),
                mScale.getY() * v.getY(),
                mScale.getZ() * v.getZ()
        );
    }

    public void rotate(float x, float y, float z) {
        rotate(new Vector3(x, y, z));
    }

    public void rotate(@NotNull Vector3 eulerAngles) {
        mRotation = mRotation.rotate(eulerAngles);
    }

    public void rotate(@NotNull Quaternion rotation) {
        mRotation = mRotation.dot(rotation);
    }

    public void setLocation(float x, float y, float z) {
        mLocation = new Vector3(x, y, z);
    }

    public void setLocation(@NotNull Vector3 location) {
        mLocation = location;
    }

    public void setRotation(float x, float y, float z) {
        mRotation = Quaternion.fromEulerAngles(x, y, z);
    }

    public void setRotation(@NotNull Vector3 eulerAngles) {
        mRotation = Quaternion.fromEulerAngles(eulerAngles);
    }

    public void setRotation(@NotNull Quaternion rotation) {
        mRotation = rotation;
    }

    public void setScale(float x, float y, float z) {
        mScale = new Vector3(x, y, z);
    }

    public void setScale(@NotNull Vector3 scale) {
        mScale = scale;
    }

    @NotNull public Quaternion getRotation() { return mRotation; }
    @NotNull public Vector3 getLocation() { return mLocation; }
    @NotNull public Vector3 getScale() { return mScale; }

    @NotNull public Vector3 getViewDirection() {
        return mRotation.apply(Vector3.ORT_Z);
    }

    @NotNull public Matrix4 getMatrix() {
        return Matrix4.getTranslationMatrix(mLocation)
                .cross(mRotation.getRotationMatrix())
                .cross(Matrix4.getScaleMatrix(mScale));
    }
}
