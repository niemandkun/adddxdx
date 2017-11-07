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

package org.adddxdx.clocks;

import org.adddxdx.engine.ActiveSystem;
import org.adddxdx.engine.Clock;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TicksMeasureSystem extends ActiveSystem<TicksMeasureSystem.Component> implements Clock {
    private final Set<Component> mComponents;
    private long mTotalTicks;

    public TicksMeasureSystem() {
        mTotalTicks = 0;
        mComponents = new HashSet<>();
    }

    @Override
    public long getTime() {
        return mTotalTicks;
    }

    @Override
    public void update(Duration timeSinceLastUpdate) {
        mTotalTicks++;
        new ArrayList<>(mComponents)
                .forEach(c -> c.onTick(timeSinceLastUpdate, mTotalTicks));
    }

    @Override
    public void register(Component component) {
        mComponents.add(component);
    }

    @Override
    public void unregister(Component component) {
        mComponents.remove(component);
    }

    static abstract class Component extends org.adddxdx.engine.Component {
        abstract void onTick(Duration timeSinceLastTick, long totalTicksCount);
    }
}
