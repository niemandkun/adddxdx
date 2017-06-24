package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public class Projection {
    public static @NotNull Matrix4 perspective(float fovY, float aspect, float zNear, float zFar) {
        float f = 1.0f / (float) Math.tan(fovY / 2);

        return new Matrix4(
                f/aspect, 0, 0, 0,
                0, f, 0, 0,
                0, 0, (zFar + zNear)/(zNear - zFar), (2 * zFar * zNear)/(zNear - zFar),
                0, 0, -1,0
        );
    }

    public static @NotNull Matrix4 frustum(float left, float right, float bottom, float top, float near, float far) {
        float a11 = 2 * near / (right - left);
        float a22 = 2 * near / (top - bottom);
        float a13 = (right + left) / (right - left);
        float a23 = (top + bottom) / (top - bottom);
        float a33 = (far + near) / (near - far);
        float a34 = (2 * far * near) / (near - far);
        float a43 = -1;

        return new Matrix4(
                a11, 0, a13, 0,
                0, a22, a23, 0,
                0, 0, a33, a34,
                0, 0, a43, 0
        );
    }
}
