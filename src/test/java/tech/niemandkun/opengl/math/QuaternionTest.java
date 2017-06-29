package tech.niemandkun.opengl.math;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.registerCustomDateFormat;
import static org.assertj.core.data.Percentage.withPercentage;

public class QuaternionTest {
    private final static float PI = (float) Math.PI;

    @Test
    public void shouldRotateBasis_aroundOx() {
        Quaternion q = Quaternion.fromEulerAngles(PI / 2, 0, 0);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(1, 0, 0));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(0, 0, 1));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(0, -1, 0));
    }

    @Test
    public void shouldRotateBasis_aroundOy() {
        Quaternion q = Quaternion.fromEulerAngles(0, PI / 2, 0);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 0, -1));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(0, 1, 0));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(1, 0, 0));
    }

    @Test
    public void shouldRotateBasis_aroundOz() {
        Quaternion q = Quaternion.fromEulerAngles(0, 0, PI / 2);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 1, 0));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(-1, 0, 0));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(0, 0, 1));
    }

    @Test
    public void composition_should_RotateBasisCorrectly() {
        Quaternion q = Quaternion.fromEulerAngles(0, 0, PI / 2);
        q = q.dot(Quaternion.fromEulerAngles(PI / 2, 0, 0));

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 1, 0));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(0, 0, 1));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(1, 0, 0));
    }

    @Test
    public void composition_should_BeNotCommutative() {
        Quaternion first = Quaternion.fromEulerAngles(0, 0, PI / 2);
        Quaternion second = Quaternion.fromEulerAngles(PI / 2, 0, 0);

        Quaternion q = first.dot(second);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 1, 0));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(0, 0, 1));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(1, 0, 0));

        q = second.dot(first);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 0, 1));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(-1, 0, 0));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(0, -1, 0));
    }

    @Test
    public void toEulerAngles_shouldWork() {
        assertClose(Quaternion.fromEulerAngles(1, 1, 1).toEulerAngles(), new Vector3(1, 1, 1));
        assertClose(Quaternion.fromEulerAngles(1, 0, 0).toEulerAngles(), new Vector3(1, 0, 0));
        assertClose(Quaternion.fromEulerAngles(0, 1, 0).toEulerAngles(), new Vector3(0, 1, 0));
        assertClose(Quaternion.fromEulerAngles(0, 0, 1).toEulerAngles(), new Vector3(0, 0, 1));
    }

    private static void assertClose(Vector3 actual, Vector3 expected) {
        assertThat(actual.getX()).isCloseTo(expected.getX(), withPercentage(0.01));
        assertThat(actual.getY()).isCloseTo(expected.getY(), withPercentage(0.01));
        assertThat(actual.getZ()).isCloseTo(expected.getZ(), withPercentage(0.01));
    }
}
