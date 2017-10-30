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

package org.adddxdx.briefexample;

import org.adddxdx.engine.Setting;
import org.adddxdx.engine.Story;
import org.adddxdx.io.Platform;
import org.adddxdx.math.Size;

public class EntryPoint {
    public static void main(String[] args) {
        Platform myLaptop = Platform.tweaker()
                .onWindow().setResizable(false).setMaximized(true)
                .onVideoMode().setSize(new Size(1368, 768)).setVsyncEnabled(false)
                .onContext().setMajorVersion(3).setMinorVersion(3)
                .andEverythingElseIsDefault();

        Story.basedOn(Setting.preparedFor(myLaptop).withAttentionAndCare()).reveal(TestingScene.class);
    }
}
