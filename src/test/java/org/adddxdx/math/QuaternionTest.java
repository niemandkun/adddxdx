/*
 * Copyright (C) 2017 Poroshin Ivan
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.math;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.within;

public class QuaternionTest {
    private final static float PRECISE_OF_COMPARISONS = 0.0001f;

    @Test
    public void shouldRotateBasis_aroundOx() {
        Quaternion q = Quaternion.fromEulerAngles(FMath.HALF_PI, 0, 0);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(1, 0, 0));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(0, 0, 1));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(0, -1, 0));
    }

    @Test
    public void shouldRotateBasis_aroundOy() {
        Quaternion q = Quaternion.fromEulerAngles(0, FMath.HALF_PI, 0);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 0, -1));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(0, 1, 0));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(1, 0, 0));
    }

    @Test
    public void shouldRotateBasis_aroundOz() {
        Quaternion q = Quaternion.fromEulerAngles(0, 0, FMath.HALF_PI);

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 1, 0));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(-1, 0, 0));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(0, 0, 1));
    }

    @Test
    public void composition_should_RotateBasisCorrectly() {
        Quaternion q = Quaternion.fromEulerAngles(0, 0, FMath.HALF_PI);
        q = q.dot(Quaternion.fromEulerAngles(FMath.HALF_PI, 0, 0));

        assertClose(q.apply(new Vector3(1, 0, 0)), new Vector3(0, 1, 0));
        assertClose(q.apply(new Vector3(0, 1, 0)), new Vector3(0, 0, 1));
        assertClose(q.apply(new Vector3(0, 0, 1)), new Vector3(1, 0, 0));
    }

    @Test
    public void composition_should_BeNotCommutative() {
        Quaternion first = Quaternion.fromEulerAngles(0, 0, FMath.HALF_PI);
        Quaternion second = Quaternion.fromEulerAngles(FMath.HALF_PI, 0, 0);

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

    @Test
    public void rotationMatrix_should_BeExactlyAsRotationMatrixFromMatrixMethod() {
        assertClose(Quaternion.fromEulerAngles(FMath.HALF_PI, 0, 0).getRotationMatrix(),
                Matrix4.getRotationMatrix(FMath.HALF_PI, 0, 0));

        assertClose(Quaternion.fromEulerAngles(0, FMath.HALF_PI, 0).getRotationMatrix(),
                Matrix4.getRotationMatrix(0, FMath.HALF_PI, 0));

        assertClose(Quaternion.fromEulerAngles(0, 0, FMath.HALF_PI).getRotationMatrix(),
                Matrix4.getRotationMatrix(0, 0, FMath.HALF_PI));
    }

    private static void assertClose(Matrix4 actual, Matrix4 expected) {
        float[] diff = actual.sub(expected).toFloatArray();

        for (float element : diff)
            assertThat(element).isCloseTo(0, within(PRECISE_OF_COMPARISONS));
    }

    private static void assertClose(Vector actual, Vector expected) {
        float[] actualArray = actual.toFloatArray();
        float[] expectedArray = expected.toFloatArray();

        assertThat(actualArray.length).isEqualTo(expectedArray.length);

        for (int i = 0; i < actualArray.length; ++i)
            assertThat(actualArray[i]).isCloseTo(expectedArray[i], within(PRECISE_OF_COMPARISONS));
    }
}
