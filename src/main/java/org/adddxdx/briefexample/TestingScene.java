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

import org.adddxdx.engine.Scene;

public class TestingScene extends Scene {
    private static final int CELL_SIZE = 5;

    @Override
    public void onCreate() {
        spawnActor(SkyFrame.class);
        spawnActor(Player.class);
        spawnActor(Floor.class);
        spawnActor(TestingLight.class);
        spawnActor(DebugGui.class);

        spawnActor(Triangles.class).getTransform().translate(-CELL_SIZE, 1, 0);
        spawnActor(House.class).getTransform().translate(0, 0, 0);
        spawnActor(PieceOfGlass.class).getTransform().translate(CELL_SIZE, 0, 0);

        for (int x = -CELL_SIZE; x <= CELL_SIZE; x += CELL_SIZE) {
            spawnActor(Cylinder.class).getTransform().translate(x, 1, CELL_SIZE);
        }
    }
}
