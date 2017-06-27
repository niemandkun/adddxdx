package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Vector2 implements Vector<Vector2> {
    public static final Vector2 ORT_X = new Vector2(1, 0);
    public static final Vector2 ORT_Y = new Vector2(0, 1);
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 ONE = new Vector2(1, 1);

    public static final Vector2 LEFT = ORT_X;
    public static final Vector2 RIGHT = LEFT.negate();

    public static final Vector2 UP = ORT_Y;
    public static final Vector2 DOWN = UP.negate();

    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public @NotNull float[] toFloatArray() {
        return new float[] {x, y};
    }

    public @NotNull void toFloatArray(float[] outArray) {
        outArray[0] = x;
        outArray[1] = y;
    }

    public static @NotNull Vector2 fromFloatArray(@NotNull float[] vector) {
        return new Vector2(vector[0], vector[1]);
    }

    public float chessboardDistance(@NotNull Vector2 other) {
        return Math.max(Math.abs(x - other.x), Math.abs(y - other.y));
    }

    public @NotNull Vector2 getNormal() {
        return new Vector2(-y, x);
    }

    public @NotNull Vector2 rotate(float angle) {
        return new Vector2(
                x * (float) Math.cos(angle) + y * (float) Math.sin(angle),
              - x * (float) Math.sin(angle) + y * (float) Math.cos(angle)
        );
    }

    @Override
    public @NotNull Vector2 add(@NotNull Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    @Override
    public @NotNull Vector2 sub(@NotNull Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    @Override
    public @NotNull Vector2 mul(float k) {
        return new Vector2(x * k, y * k);
    }

    @Override
    public @NotNull Vector2 div(float k) {
        return new Vector2(x / k, y / k);
    }

    @Override
    public float dot(@NotNull Vector2 other) {
        return x * other.x + y * other.y;
    }

    public float cross(@NotNull Vector2 other) {
        return x * other.y - y * other.x;
    }

    public float angle() {
        return (float) Math.atan2(y, x);
    }

    public @NotNull Vector4 toVec4(float z, float w) {
        return new Vector4(x, y, z, w);
    }

    public @NotNull Vector4 toVec4() {
        return new Vector4(x, y, 0, 0);
    }

    public @NotNull Vector3 toVec3(float z) {
        return new Vector3(x, y, z);
    }

    public @NotNull Vector3 toVec3() {
        return new Vector3(x, y, 0);
    }

    public int getOctant() {
        if (y > 0) {
            if (x > 0) {
                if (x > y) return 0;
                return 1;
            } else {
                if (y*y > x*x) return 2;
                return 3;
            }
        } else {
            if (x < 0) {
                if (x < y) return 4;
                return 5;
            } else {
                if (y*y > x*x) return 6;
                return 7;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2 vector2i = (Vector2) o;

        return x == vector2i.x && y == vector2i.y;
    }

    @Override
    public int hashCode() {
        return 1023 * Float.hashCode(x) + Float.hashCode(y);
    }

    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public Vector2 setX(float x) { return new Vector2(x, y); }
    public Vector2 setY(float y) { return new Vector2(x, y); }
}
