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

import org.adddxdx.fio.WavefrontObject;
import org.adddxdx.math.Transform;

import java.util.*;
import java.util.stream.Collectors;

public class Actor implements ShortLifecycle {
    private final Map<Class<? extends Component>, List<Component>> mComponents;
    private final Transform mTransform;
    private Scene mScene;

    public Actor() {
        mComponents = new HashMap<>();
        mTransform = new Transform();
    }

    public Collection<? extends Component> getComponents() {
        return mComponents.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Transform getTransform() {
        return mTransform;
    }

    public Scene getScene() {
        return mScene;
    }

    void setScene(Scene scene) {
        mScene = scene;
    }

    public Resources getResources() {
        return mScene.getResources();
    }

    public void addComponent(@NotNull Component component) {
        if (component.getActor() != null) {
            throw new IllegalArgumentException("Trying to add a Component, but it has already been added to an Actor.");
        }
        List<Component> components = mComponents.get(component.getClass());
        if (components == null) {
            components = new ArrayList<>();
        }
        component.setActor(this);
        component.onCreate();
        components.add(component);
        mComponents.put(component.getClass(), components);
    }

    public void removeComponent(Component component) {
        List<Component> components = mComponents.get(component.getClass());
        if (components != null) {
            component.onDestroy();
            component.setActor(null);
            components.remove(component);
        }
    }

    public void removeComponent(Class<? extends Component> componentClass) {
        List<Component> components = mComponents.get(componentClass);
        if (components == null) {
            return;
        }
        for (Component component : components) {
            component.onDestroy();
            component.setActor(null);
        }
        mComponents.remove(componentClass);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        for (List<Component> components : mComponents.values()) {
            for (Component component : components) {
                component.onDestroy();
            }
        }
        mComponents.clear();
    }
}
