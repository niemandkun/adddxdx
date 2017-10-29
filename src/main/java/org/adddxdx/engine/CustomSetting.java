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

import org.adddxdx.graphics.MaterialFactory;
import org.adddxdx.graphics.support.primitives.PrimitivesFactory;
import org.adddxdx.io.Window;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

class CustomSetting implements Setting {
    @Override public PrimitivesFactory getPrimitivesFactory() { return mPrimitivesFactory; }
    @Override public MaterialFactory getMaterialFactory() { return mMaterialFactory; }
    @Override public Scenario getScenario() { return mScenario; }
    @Override public Window getWindow() { return mWindow; }
    @Override public Clock getClock() { return mClock; }

    private final PrimitivesFactory mPrimitivesFactory;
    private final MaterialFactory mMaterialFactory;
    private final Scenario mScenario;
    private final Window mWindow;
    private final Clock mClock;

    @Override public Collection<ActiveSystem> getActiveSystems() { return mActiveSystems; }
    @Override public Collection<SystemInfo> getAllSystems() { return mAllSystems; }

    private final Collection<ActiveSystem> mActiveSystems;
    private final Collection<SystemInfo> mAllSystems;

    CustomSetting(ServiceLocator locator) {
        mScenario = new Scenario(this);
        mPrimitivesFactory = locator.get(PrimitivesFactory.class);
        mMaterialFactory = locator.get(MaterialFactory.class);
        mActiveSystems = locator.getAll(ActiveSystem.class);
        mWindow = locator.get(Window.class);
        mClock = locator.get(Clock.class);

        mAllSystems = getSystems(locator.getAll(System.class));
    }

    private List<SystemInfo> getSystems(Collection<System> systems) {
        List<SystemInfo> allSystems = new ArrayList<>();

        for (System system : systems) {
            Type genericParameterType = getGenericInterfaceParameter(system.getClass());
            allSystems.add(new SystemInfo((Class) genericParameterType, system));
        }

        return allSystems;
    }

    private Type getGenericInterfaceParameter(Class<?> clazz) {
        ParameterizedType firstGenericInterface = (ParameterizedType) clazz.getGenericSuperclass();
        return firstGenericInterface.getActualTypeArguments()[0];
    }
}
