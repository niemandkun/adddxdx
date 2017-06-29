package tech.niemandkun.opengl.math;

public class FMath {
    public static final float E = (float) Math.E;
    public static final float PI = (float) Math.PI;
    public static final float HALF_PI = (float) Math.PI / 2;
    public static final float TWO_PI = 2 * (float) Math.PI;

    public static float clamp(float source, float min, float max) {
        if (source < min) return min;
        if (source > max) return max;
        return source;
    }
}
