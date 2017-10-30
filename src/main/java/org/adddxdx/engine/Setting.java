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

package org.adddxdx.engine;

import org.adddxdx.graphics.GraphicsSystem;
import org.adddxdx.graphics.MaterialFactory;
import org.adddxdx.graphics.support.primitives.PrimitivesFactory;
import org.adddxdx.io.InputSystem;
import org.adddxdx.io.Keyboard;
import org.adddxdx.io.Mouse;
import org.adddxdx.io.Platform;
import org.adddxdx.io.Window;

import java.util.Collection;
import java.util.List;

public interface Setting {
    <TService> TService get(Class<TService> clazz);
    <TService> Collection<TService> getAll(Class<TService> clazz);
    List<SystemInfo> describeInternals();

    static SettingBuilder preparedFor(Platform platform) {
        return new SettingImplBuilder()
                .putSingle(InputSystem.class, platform::getInputSystem)
                .putSingle(Keyboard.class, platform::getKeyboard)
                .putSingle(Window.class, platform::getWindow)
                .putSingle(Mouse.class, platform::getMouse)
                .putSingle(GraphicsSystem.class, GraphicsSystem::new)
                .putSingle(Resources.class, ResourcesImpl::new)
                .putSingle(Scenario.class, ScenarioImpl::new)
                .put(new PrimitivesFactory())
                .put(new MaterialFactory())
                .put(new ClockImpl());
    }
}
