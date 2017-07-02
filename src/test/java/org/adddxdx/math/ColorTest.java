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

public class ColorTest {
    @Test
    public void parse_shouldWorkWith_RgbWithHash() {
        Color color = Color.parse("#3a2b1c");
        assertThat(color).isEqualToComparingFieldByField(new Color(0x3a, 0x2b, 0x1c, 0xff));
    }

    @Test
    public void parse_shouldWorkWith_ArgbWithHash() {
        Color color = Color.parse("#3a2b1cff");
        assertThat(color).isEqualToComparingFieldByField(new Color(0x3a, 0x2b, 0x1c, 0xff));
    }

    @Test
    public void parse_shouldWorkWith_RgbWithoutHash() {
        Color color = Color.parse("3a2b1c");
        assertThat(color).isEqualToComparingFieldByField(new Color(0x3a, 0x2b, 0x1c, 0xff));
    }

    @Test
    public void parse_shouldWorkWith_ArgbWithoutHash() {
        Color color = Color.parse("3a2b1cff");
        assertThat(color).isEqualToComparingFieldByField(new Color(0x3a, 0x2b, 0x1c, 0xff));
    }

    @Test
    public void toString_should_returnRgbHexStringWithHash() {
        Color color = new Color(0x1a, 0x2b, 0x3c, 0xff);
        assertThat(color.toString()).isEqualTo("#1a2b3cff");
    }

    @Test
    public void equals_shouldBeTrue_ifRgbAreEquals() {
        Color first = new Color(0x1a, 0x2b, 0x3c);
        Color second = new Color(0x1a, 0x2b, 0x3c);
        assertThat(first.equals(second)).isTrue();
    }

    @Test
    public void equals_shouldBeFalse_ifAtLeastOneChannelDoesNotMatch() {
        Color first = new Color(0x1a, 0x2b, 0x3c);
        Color wrongRed = new Color(0xa1, 0x2b, 0x3c);
        Color wrongBlue = new Color(0x1a, 0xb2, 0x3c);
        Color wrongGreen = new Color(0x1a, 0x2b, 0xc3);

        assertThat(first.equals(wrongRed)).isFalse();
        assertThat(first.equals(wrongGreen)).isFalse();
        assertThat(first.equals(wrongBlue)).isFalse();
    }
}
