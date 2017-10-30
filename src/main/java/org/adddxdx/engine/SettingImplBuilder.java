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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

class SettingImplBuilder implements SettingBuilder {
    private final Map<Class, Function> mLocator;

    SettingImplBuilder() {
        mLocator = new HashMap<>();
    }

    @Override
    public <TService> SettingBuilder putFactory(Class<TService> clazz, Supplier<TService> factory) {
        mLocator.put(clazz, e -> factory.get());
        return this;
    }

    @Override
    public <TService> SettingBuilder putFactory(Class<TService> clazz, Function<Setting, TService> factory) {
        mLocator.put(clazz, factory);
        return this;
    }

    @Override
    public SettingBuilder put(Object singleton) {
        mLocator.put(singleton.getClass(), e -> singleton);
        return this;
    }

    @Override
    public <TService> SettingBuilder putSingle(Class<TService> clazz, Supplier<TService> singleton) {
        mLocator.put(clazz, new SingletonGetter<>(e -> singleton.get()));
        return this;
    }

    @Override
    public <TService> SettingBuilder putSingle(Class<TService> clazz, Function<Setting, TService> singleton) {
        mLocator.put(clazz, new SingletonGetter<>(singleton));
        return this;
    }

    @Override
    public Setting withAttentionAndCare() {
        SettingImpl setting = new SettingImpl(mLocator);
        setting.addAll(convertToSystemInfo(setting.getAll(System.class)));
        return setting;
    }

    private List<SystemInfo> convertToSystemInfo(Collection<? extends System> systems) {
        List<SystemInfo> allSystemInfos = new ArrayList<>();
        for (System system : systems) {
            Type genericParameterType = getGenericInterfaceParameter(system.getClass());
            allSystemInfos.add(new SystemInfo((Class) genericParameterType, system));
        }
        return allSystemInfos;
    }

    private Type getGenericInterfaceParameter(Class<?> clazz) {
        ParameterizedType firstGenericInterface = (ParameterizedType) clazz.getGenericSuperclass();
        return firstGenericInterface.getActualTypeArguments()[0];
    }

    private class SingletonGetter<TService> implements Function<Setting, TService> {
        private final Function<Setting, TService> mCreator;
        private TService mInstance;

        SingletonGetter(Function<Setting, TService> creator) {
            mCreator = creator;
        }

        @Override
        public TService apply(Setting setting) {
            if (mInstance == null) {
                mInstance = mCreator.apply(setting);
            }
            return mInstance;
        }
    }
}
