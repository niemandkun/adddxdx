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

import org.adddxdx.engine.*;
import org.adddxdx.graphics.GraphicsSystem;
import org.adddxdx.graphics.MaterialFactory;
import org.adddxdx.graphics.support.primitives.PrimitivesFactory;
import org.adddxdx.io.*;
import org.adddxdx.math.Size;

public class EntryPoint {
    private static ServiceLocator createLocator() {
        Platform platform = Platform.builder()
                .onWindow().setResizable(false)
                .onVideoMode().setSize(new Size(1920, 1080))
                .onContext().setMajorVersion(3).setMinorVersion(3)
                .onFramebuffer().setMultiSampling(4)
                .build();

        Window window = platform.getWindow();
        MaterialFactory materialFactory = new MaterialFactory();
        PrimitivesFactory primitivesFactory = new PrimitivesFactory();
        GraphicsSystem graphicsSystem = new GraphicsSystem(window, materialFactory, primitivesFactory);

        ServiceLocator locator = new ServiceLocator();
        locator.registerSingleton(Window.class, platform.getWindow());
        locator.registerSingleton(InputSystem.class, platform.getInputSystem());
        locator.registerSingleton(MaterialFactory.class, materialFactory);
        locator.registerSingleton(PrimitivesFactory.class, primitivesFactory);
        locator.registerSingleton(GraphicsSystem.class, graphicsSystem);

        return locator;
    }

    public static void main(String[] args) {
        ServiceLocator locator = createLocator();
        Story.basedOn(Setting.from(locator)).reveal(TestingScene.class);
    }
}
