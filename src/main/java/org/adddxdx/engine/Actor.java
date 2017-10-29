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

import com.sun.istack.internal.NotNull;
import org.adddxdx.math.Transform;

import java.util.*;

public class Actor implements ShortLifecycle {
    private final Map<Class<? extends Component>, Component> mComponents = new HashMap<>();
    public Collection<? extends Component> getComponents() { return mComponents.values(); }

    private final Transform mTransform = new Transform();
    public Transform getTransform() { return mTransform; }

    private Scene mScene;
    public Scene getScene() { return mScene; }
    void setScene(Scene scene) { mScene = scene; }

    public void addComponent(@NotNull Component component) {
        if (component.getActor() != null)
            throw new IllegalArgumentException("Trying to add a Component, but it has already been added to an Actor.");

        component.setActor(this);
        component.onCreate();
        mComponents.put(component.getClass(), component);
    }

    public <TComponent extends Component> TComponent getComponent(Class<TComponent> componentClass) {
        return (TComponent) mComponents.get(componentClass);
    }

    public void removeComponent(Class<? extends Component> componentClass) {
        if (!mComponents.containsKey(componentClass)) return;

        Component component = mComponents.get(componentClass);
        component.onDestroy();
        component.setActor(null);
        mComponents.remove(componentClass);
    }

    @Override
    public void onCreate() { }

    @Override
    public void onDestroy() {
        for (Component component : mComponents.values())
            component.onDestroy();

        mComponents.clear();
    }
}
