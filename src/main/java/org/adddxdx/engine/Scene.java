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

import java.util.*;

public abstract class Scene implements FullLifecycle {

    /** Internal scene API */
    Collection<SystemInfo> getAllSystems() { return mSetting.getAllSystems(); }
    void setSetting(Setting setting) { mSetting = setting; }

    /** User available scene API */
    public PrimitivesFactory getPrimitivesFactory() { return mSetting.getPrimitivesFactory(); }
    public MaterialFactory getMaterialFactory() { return mSetting.getMaterialFactory(); }
    public Scenario getScenario() { return mSetting.getScenario(); }
    public Collection<? extends Actor> getActors() { return mActors; }
    public Clock getClock() { return mSetting.getClock(); }

    private List<Actor> mActors;
    private Setting mSetting;

    public Scene() {
        mActors = new ArrayList<>();
    }

    /** Lifecycle methods */
    @Override public void onCreate() { }
    @Override public void onResume() { }
    @Override public void onMainLoop() { }
    @Override public void onPause() { }
    @Override public void onDestroy() { removeAllActors(); }

    private void removeAllActors() {
        for (Actor actor : mActors)
            actor.onDestroy();

        mActors.clear();
    }

    /** Actors management */
    public <T extends Actor> T spawnActor(Class<T> actorClass) {
        T actor;

        try {
            actor = actorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Exception has occurred while spawning Actor, " + e.getMessage());
        }

        actor.setScene(this);
        actor.onCreate();

        mActors.add(actor);

        return actor;
    }

    public void removeActor(Actor actor) {
        if (actor.getScene() != this)
            throw new RuntimeException("Cannot remove Actor which belongs to another Scene.");

        actor.onDestroy();
        actor.setScene(null);
        mActors.remove(actor);
    }
}
