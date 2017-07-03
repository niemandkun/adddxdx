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
    @Override
    public void onCreate() {
        spawnActor(SkyFrame.class);
        spawnActor(Player.class);
        spawnActor(Walls.class);
        spawnActor(Cylinder.class).getTransform().translate(5, 1, 0);
        spawnActor(Cylinder.class).getTransform().translate(5, 1, 5);
        spawnActor(Cylinder.class).getTransform().translate(0, 1, 5);
        spawnActor(Cylinder.class).getTransform().translate(-1, 1, -3);
        spawnActor(PieceOfGlass.class).getTransform().translate(2.5f, 0, 0);
        spawnActor(FireHydrant.class);
        spawnActor(Triangles.class);
        spawnActor(TestingLight.class);
        spawnActor(DebugGui.class);
    }
}
