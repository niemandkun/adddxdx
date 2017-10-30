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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Scene implements FullLifecycle {
    private List<Actor> mActors;
    private Setting mSetting;

    public Scene() {
        mActors = new ArrayList<>();
    }

    public Resources getResources() {
        return mSetting.get(Resources.class);
    }

    public Scenario getScenario() {
        return mSetting.get(Scenario.class);
    }

    public Clock getClock() {
        return mSetting.get(Clock.class);
    }

    Setting getSetting() {
        return mSetting;
    }

    void setSetting(Setting setting) {
        mSetting = setting;
    }

    public Collection<? extends Actor> getActors() {
        return mActors;
    }

    @Override public void onCreate() {

    }

    @Override public void onResume() {

    }

    @Override public void onMainLoop() {

    }

    @Override public void onPause() {

    }

    @Override public void onDestroy() {
        removeAllActors();
    }

    private void removeAllActors() {
        for (Actor actor : mActors) {
            actor.onDestroy();
        }
        mActors.clear();
    }

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
        if (actor.getScene() != this) {
            throw new RuntimeException("Cannot remove Actor which belongs to another Scene.");
        }
        actor.onDestroy();
        actor.setScene(null);
        mActors.remove(actor);
    }
}
