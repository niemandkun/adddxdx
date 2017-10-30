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

package org.adddxdx.io;

import org.adddxdx.engine.ActiveSystem;

import java.time.Duration;

public class InputSystem extends ActiveSystem<InputSystem.Component> {
    private static final Duration INPUT_UPDATE_INTERVAL = Duration.ofMillis(25);

    private final GlfwKeyboard mKeyboard;
    private final GlfwMouse mMouse;

    InputSystem(GlfwKeyboard keyboard, GlfwMouse mouse) {
        mKeyboard = keyboard;
        mMouse = mouse;
    }

    @Override
    protected Duration getUpdateInterval() {
        return INPUT_UPDATE_INTERVAL;
    }

    @Override
    public void register(Component component) {
        component.connect(mKeyboard, mMouse);
    }

    @Override
    public void unregister(Component component) {
        component.disconnect(mKeyboard, mMouse);
    }

    @Override
    public void update(Duration timeSinceLastUpdate) {
        mKeyboard.update(timeSinceLastUpdate);
        mMouse.update();
    }

    public abstract static class Component extends org.adddxdx.engine.Component {
        abstract void connect(Keyboard keyboard, Mouse mouse);
        abstract void disconnect(Keyboard keyboard, Mouse mouse);
    }
}
