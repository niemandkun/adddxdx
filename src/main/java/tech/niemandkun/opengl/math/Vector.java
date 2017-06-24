package tech.niemandkun.opengl.math;

import com.sun.istack.internal.NotNull;

public interface Vector<TVector extends Vector<TVector>> {
    @NotNull TVector add(@NotNull TVector other);
    @NotNull TVector sub(@NotNull TVector other);
    @NotNull TVector mul(float k);
    @NotNull TVector div(float k);

    float dot(@NotNull TVector other);

    default float length() {
        return (float) Math.sqrt(length2());
    }

    default float length2() {
        return dot((TVector) this);
    }

    default float distance(TVector other) {
        return (float) Math.sqrt(distance2(other));
    }

    default float distance2(TVector other) {
        return sub(other).length();
    }

    default float angle(TVector other) {
        return (float) Math.acos(this.dot(other) / this.length() / other.length());
    }

    default @NotNull TVector normalize() {
        return div(length());
    }
}
