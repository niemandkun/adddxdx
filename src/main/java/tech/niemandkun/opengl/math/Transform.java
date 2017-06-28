package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Transform {
    private Vector3 mLocation;
    private Vector3 mRotation;
    private Vector3 mScale;

    // TODO: Implement quaternions for this
    private Matrix4 mRotationMatrix;

    public Transform() {
        mLocation = Vector3.ZERO;
        mRotation = Vector3.ZERO;
        mScale = Vector3.ONE;
        mRotationMatrix = Matrix4.IDENTITY;
    }

    public void rotate(float x, float y, float z) {
        rotate(new Vector3(x, y, z));
    }

    public void translate(float x, float y, float z) {
        translate(new Vector3(x, y, z));
    }

    public void scale(float x, float y, float z) {
        scale(new Vector3(x, y, z));
    }

    public void rotate(Vector3 v) {
        mRotationMatrix = mRotationMatrix.cross(Matrix4.getRotationMatrix(v));
        mRotation = mRotation.add(v);
    }

    public void translate(Vector3 v) {
        mLocation = mLocation.add(v);
    }

    public void scale(Vector3 v) {
        mScale = new Vector3(
                mScale.getX() * v.getX(),
                mScale.getY() * v.getY(),
                mScale.getZ() * v.getZ()
        );
    }

    public void setLocation(float x, float y, float z) {
        mLocation = new Vector3(x, y, z);
    }

    public void setLocation(@NotNull Vector3 location) {
        mLocation = location;
    }

    public void setRotation(float x, float y, float z) {
        mRotation = new Vector3(x, y, z);
        mRotationMatrix = Matrix4.getRotationMatrix(mRotation);
    }

    public void setRotation(@NotNull Vector3 rotation) {
        mRotation = rotation;
        mRotationMatrix = Matrix4.getRotationMatrix(rotation);
    }

    public void setScale(float x, float y, float z) {
        mScale = new Vector3(x, y, z);
    }

    public void setScale(@NotNull Vector3 scale) {
        mScale = scale;
    }

    @NotNull public Vector3 getLocation() { return mLocation; }
    @NotNull public Vector3 getRotation() { return mRotation; }
    @NotNull public Vector3 getScale() { return mScale; }
    @NotNull public Matrix4 getMatrix() { return buildMatrix(mLocation, mScale); }

    @NotNull public Vector3 getViewDirection() {
        return mRotationMatrix.cross(Vector4.ORT_Z).clipToVec3();
    }

    private Matrix4 buildMatrix(Vector3 location, Vector3 scale) {
        return Matrix4.getTranslationMatrix(location)
                .cross(mRotationMatrix)
                .cross(Matrix4.getScaleMatrix(scale));
    }
}
