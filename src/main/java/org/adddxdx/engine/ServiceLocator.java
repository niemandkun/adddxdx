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

import java.util.*;
import java.util.function.Supplier;

public class ServiceLocator {
    private final Map<Class, Supplier> mLocator;

    public ServiceLocator() {
        mLocator = new HashMap<>();
    }

    public <TService> void registerFactory(Class<TService> clazz, Supplier<TService> factory) {
        mLocator.put(clazz, factory);
    }

    public <TService> void registerSingleton(Class<TService> clazz, TService singleton) {
        mLocator.put(clazz, () -> singleton);
    }

    public <TService> TService get(Class<TService> clazz) {
        if (mLocator.containsKey(clazz))
            return (TService) mLocator.get(clazz).get();

        for (Class inLocator : mLocator.keySet()) {
            if (clazz.isAssignableFrom(inLocator))
                return (TService) mLocator.get(inLocator).get();
        }

        throw new IllegalArgumentException("Class " + clazz + " is not found in locator.");
    }

    public <TService> Collection<TService> getAll(Class<TService> clazz) {
        List<TService> listOfMatchingServices = new ArrayList<>();

        for (Class inLocator : mLocator.keySet()) {
            if (clazz.isAssignableFrom(inLocator))
                listOfMatchingServices.add((TService) mLocator.get(inLocator).get());
        }

        return listOfMatchingServices;
    }
}
