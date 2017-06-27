package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Vector3 implements Vector<Vector3> {
    public static final Vector3 ORT_X = new Vector3(1, 0, 0);
    public static final Vector3 ORT_Y = new Vector3(0, 1, 0);
    public static final Vector3 ORT_Z = new Vector3(0, 0, 1);
    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 ONE = new Vector3(1, 1, 1);

    public static final Vector3 LEFT = ORT_X;
    public static final Vector3 RIGHT = LEFT.negate();

    public static final Vector3 UP = ORT_Y;
    public static final Vector3 DOWN = UP.negate();

    public static final Vector3 FORWARD = ORT_Z;
    public static final Vector3 BACKWARD = FORWARD.negate();

    private final float x;
    private final float y;
    private final float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public @NotNull float[] toFloatArray() {
        return new float[] {x, y, z};
    }

    public @NotNull void toFloatArray(float[] outArray) {
        outArray[0] = x;
        outArray[1] = y;
        outArray[2] = z;
    }

    public static @NotNull Vector3 fromFloatArray(@NotNull float[] vector) {
        return new Vector3(vector[0], vector[1], vector[2]);
    }

    @Override
    public @NotNull Vector3 mul(float k) {
        return new Vector3(x * k, y * k, z * k);
    }

    @Override
    public @NotNull Vector3 div(float k) {
        return new Vector3(x / k, y / k, z / k);
    }

    @Override
    public @NotNull Vector3 sub(@NotNull Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    @Override
    public @NotNull Vector3 add(@NotNull Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    @Override
    public float dot(@NotNull Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector3 cross(@NotNull Vector3 other) {
        return new Vector3(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    public float distanceTo(@NotNull Vector3 other) {
        return this.sub(other).length();
    }

    public @NotNull Vector4 toVec4(float w) {
        return new Vector4(x, y, z, w);
    }

    public @NotNull Vector4 toVec4() {
        return new Vector4(x, y, z, 0);
    }

    public @NotNull Vector2 clipToVec2() {
        return new Vector2(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Vector3 vector3 = (Vector3) other;

        return Float.compare(vector3.x, x) == 0
                && Float.compare(vector3.y, y) == 0
                && Float.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z +")";
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }

    public Vector3 setX(float x) { return new Vector3(x, y, z); }
    public Vector3 setY(float y) { return new Vector3(x, y, z); }
    public Vector3 setZ(float z) { return new Vector3(x, y, z); }
}
