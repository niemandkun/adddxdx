package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Matrix4 {
    private final float[][] mat;

    public Matrix4(
            float a11, float a12, float a13, float a14,
            float a21, float a22, float a23, float a24,
            float a31, float a32, float a33, float a34,
            float a41, float a42, float a43, float a44) {

        mat = new float[][] {
                {a11, a12, a13, a14},
                {a21, a22, a23, a24},
                {a31, a32, a33, a34},
                {a41, a42, a43, a44},
        };
    }

    private Matrix4(@NotNull float[][] mat) {
        this.mat = mat;
    }

    public @NotNull Matrix4 cross(@NotNull Matrix4 other) {
        return new Matrix4(fastMultiply(this.mat, other.mat));
    }

    private static float[][] fastMultiply(@NotNull float[][] a, @NotNull float[][] b) {
        float[][] mat = new float[4][4];

        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                mat[i][j] =
                          a[i][0] * b[0][j]
                        + a[i][1] * b[1][j]
                        + a[i][2] * b[2][j]
                        + a[i][3] * b[3][j];

        return mat;
    }

    public @NotNull Vector4 cross(@NotNull Vector4 vector) {
        return new Vector4(fastMultiply(this.mat, vector.toFloatArray()));
    }

    private static @NotNull float[] fastMultiply(@NotNull float[][] a, @NotNull float[] v) {
        float[] vec = new float[4];

        for (int i = 0; i < 4; ++i)
            vec[i] =
                      a[i][0] * v[0]
                    + a[i][1] * v[1]
                    + a[i][2] * v[2]
                    + a[i][3] * v[3];

        return vec;
    }

    public static @NotNull Matrix4 getTranslationMatrix(float x, float y, float z) {
        return new Matrix4(new float[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1},
        });
    }

    public static @NotNull Matrix4 getRotationMatrix(float x, float y, float z) {
        return new Matrix4(new float[][]
            {
                {1,                     0,                      0,                      0},
                {0,                     (float)  Math.cos(x),   (float) Math.sin(x),    0},
                {0,                     (float) -Math.sin(x),   (float) Math.cos(x),    0},
                {0,                     0,                      0,                      1},
            }
        ).cross(new Matrix4(new float[][]
            {
                {(float) Math.cos(y),   0,                      (float) -Math.sin(y),   0},
                {0,                     1,                      0,                      0},
                {(float) Math.sin(y),   0,                      (float)  Math.cos(y),   0},
                {0,                     0,                      0,                      1},
            }
        )).cross(new Matrix4(new float[][]
            {
                {(float)  Math.cos(z),  (float) Math.sin(z),    0,                      0},
                {(float) -Math.sin(z),  (float) Math.cos(z),    0,                      0},
                {0,                     0,                      1,                      0},
                {0,                     0,                      0,                      1},
            }
        ));
    }

    public static @NotNull Matrix4 getScaleMatrix(float x, float y, float z) {
        return new Matrix4(new float[][]{
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1},
        });
    }

    public static final @NotNull Matrix4 IDENTITY = new Matrix4(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    );

    public static final @NotNull Matrix4 ZERO = new Matrix4(
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
    );
}
