package org.adddxdx.math;

import com.sun.istack.internal.NotNull;

public class Transform2d {
    private final Transform mTransform;

    public Transform2d(Transform transform) {
        mTransform = transform;
    }

   public void translate(float x, float y) {
        mTransform.translate(x, y, 0);
    }

    public void translate(@NotNull Vector2 direction) {
        mTransform.translate(direction.toVec3());
    }

    public void scale(float factor) {
        mTransform.scale(factor);
    }

    public void scale(float x, float y) {
        mTransform.scale(x, y, 1);
    }

    public void scale(@NotNull Vector2 scale) {
        mTransform.scale(scale.toVec3(1));
    }

    public void rotate(float angleRads) {
        mTransform.rotate(0, 0, angleRads);
    }

    public void setLocation(float x, float y) {
        mTransform.setLocation(x, y, 0);
    }

    public void setLocation(@NotNull Vector2 location) {
        mTransform.setLocation(location.toVec3());
    }

    public void setRotation(float angleRads) {
        mTransform.setRotation(0, 0, angleRads);
    }

    public void setScale(float scale) {
        mTransform.setScale(scale);
    }

    public void setScale(float x, float y) {
        mTransform.setScale(x, y, 1);
    }

    public void setScale(@NotNull Vector2 scale) {
        mTransform.setScale(scale.toVec3(1));
    }

    public Vector2 getViewDirection() {
        float angle = mTransform.getRotation().toEulerAngles().getZ();
        return new Vector2(FMath.cos(angle), FMath.sin(angle));
    }

    @NotNull public float getRotation() { return mTransform.getRotation().toEulerAngles().getZ(); }
    @NotNull public Vector2 getLocation() { return mTransform.getLocation().clipToVec2(); }
    @NotNull public Vector2 getScale() { return mTransform.getScale().clipToVec2(); }
}
