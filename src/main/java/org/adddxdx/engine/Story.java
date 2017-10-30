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

import org.adddxdx.io.Window;

import java.util.Collection;

public class Story {
    private final Setting mSetting;

    public static Story basedOn(Setting setting) { return new Story(setting); }

    private Story(Setting setting) {
        mSetting = setting;
    }

    public void reveal(Class<? extends Scene> actFirst) {
        Collection<ActiveSystem> activeSystems = mSetting.getAll(ActiveSystem.class);
        Scenario scenario = mSetting.get(Scenario.class);
        Window window = mSetting.get(Window.class);
        ClockHandle clockHandle = mSetting.get(ClockHandle.class);

        scenario.push(actFirst);

        Scene currentAct;

        while (window.isOpen() && (currentAct = scenario.peek()) != null) {
            currentAct.onMainLoop();
            activeSystems.forEach(ActiveSystem::fixedUpdate);
            window.update();
            clockHandle.tick();
        }
    }
}
