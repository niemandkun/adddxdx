package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Transform {
    private Vector3 location;
    private Vector3 rotation;
    private Vector3 scale;

    public Transform() {
        location = Vector3.ZERO;
        rotation = Vector3.ZERO;
        scale = Vector3.ONE;
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
        rotation = rotation.add(v);
    }

    public void translate(Vector3 v) {
        location = location.add(v);
    }

    public void scale(Vector3 v) {
        scale = new Vector3(
                scale.getX() * v.getX(),
                scale.getY() * v.getY(),
                scale.getZ() * v.getZ()
        );
    }

    public void setLocation(@NotNull Vector3 location) { this.location = location; }
    public void setRotation(@NotNull Vector3 rotation) { this.rotation = rotation; }
    public void setScale(@NotNull Vector3 scale) { this.scale = scale; }

    @NotNull public Vector3 getLocation() { return location; }
    @NotNull public Vector3 getRotation() { return rotation; }
    @NotNull public Vector3 getScale() { return scale; }

    @NotNull
    public Matrix4 getMatrix() {
        return Matrix4.getTranslationMatrix(location.getX(), location.getY(), location.getZ())
                .cross(Matrix4.getRotationMatrix(rotation.getX(), rotation.getY(), rotation.getZ()))
                .cross(Matrix4.getScaleMatrix(scale.getX(), scale.getY(), scale.getZ()));
    }
}
