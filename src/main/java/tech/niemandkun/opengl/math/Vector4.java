package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

public class Vector4 implements Vector<Vector4> {
    private final float[] vec;

    public Vector4(float x, float y, float z, float w) {
        this.vec = new float[] {x, y, z, w};
    }

    public Vector4(@NotNull float[] vec) {
        if (vec.length != 4) throw new IllegalArgumentException();
        this.vec = vec;
    }

    float[] toFloatArray() {
        return vec;
    }

    public @NotNull Vector4 homogenize() {
        return new Vector4(new float[] {
                vec[0] / vec[3],
                vec[1] / vec[3],
                vec[2] / vec[3],
                1
        });
    }

    @Override
    public @NotNull Vector4 div(float value) {
        return new Vector4(new float[] {
                vec[0] / value,
                vec[1] / value,
                vec[2] / value,
                vec[3] / value
        });
    }

    @Override
    public @NotNull Vector4 mul(float value) {
        return new Vector4(new float[] {
                vec[0] * value,
                vec[1] * value,
                vec[2] * value,
                vec[3] * value
        });
    }

    @Override
    public @NotNull Vector4 add(@NotNull Vector4 other) {
        return new Vector4(new float[] {
                this.vec[0] + other.vec[0],
                this.vec[1] + other.vec[1],
                this.vec[2] + other.vec[2],
                this.vec[3] + other.vec[3],
        });
    }

    @Override
    public @NotNull Vector4 sub(@NotNull Vector4 other) {
        return new Vector4(new float[] {
                this.vec[0] - other.vec[0],
                this.vec[1] - other.vec[1],
                this.vec[2] - other.vec[2],
                this.vec[3] - other.vec[3],
        });
    }

    @Override
    public float dot(@NotNull Vector4 other) {
        return 0;
    }

    public @NotNull Vector3 clipToVec3() {
        return new Vector3(vec[0], vec[1], vec[2]);
    }

    public @NotNull Vector2 clipToVec2() {
        return new Vector2(vec[0], vec[1]);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Vector4 vector4 = (Vector4) other;

        return Arrays.equals(vec, vector4.vec);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vec);
    }

    @Override
    public String toString() {
        return "(" + vec[0] + ", "
                   + vec[1] + ", "
                   + vec[2] + ", "
                   + vec[3] + ")";
    }

    public float getX() { return vec[0]; }
    public float getY() { return vec[1]; }
    public float getZ() { return vec[2]; }
    public float getW() { return vec[3]; }

    public Vector4 setX(float x) { return new Vector4(new float[] {x, vec[1], vec[2], vec[3]}); }
    public Vector4 setY(float y) { return new Vector4(new float[] {vec[0], y, vec[2], vec[3]}); }
    public Vector4 setZ(float z) { return new Vector4(new float[] {vec[0], vec[1], z, vec[3]}); }
    public Vector4 setW(float w) { return new Vector4(new float[] {vec[0], vec[1], vec[2], w}); }
}
