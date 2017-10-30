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

import java.util.Stack;

class ScenarioImpl implements Scenario {
    private final Stack<Scene> mSceneStack;
    private final Setting mSetting;

    ScenarioImpl(Setting setting) {
        mSceneStack = new Stack<>();
        mSetting = setting;
    }

    private Scene createScene(Class<? extends Scene> sceneClass) {
        Scene instance = instantiateScene(sceneClass);
        instance.setSetting(mSetting);
        return instance;
    }

    private Scene instantiateScene(Class<? extends Scene> sceneClass) {
        try {
            return sceneClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate scene, " + e.getMessage());
        }
    }

    @Override
    public Scene peek() {
        return mSceneStack.peek();
    }

    @Override
    public void push(Class<? extends Scene> sceneClass) {
        if (!mSceneStack.isEmpty())
            mSceneStack.peek().onPause();

        Scene toPush = createScene(sceneClass);
        toPush.onCreate();
        toPush.onResume();

        mSceneStack.push(toPush);
    }

    @Override
    public void pop() {
        Scene toPop = mSceneStack.pop();
        toPop.onPause();
        toPop.onDestroy();

        if (!mSceneStack.isEmpty())
            mSceneStack.peek().onResume();
    }

    @Override
    public void clear() {
        while (!mSceneStack.isEmpty()) {
            Scene toPop = mSceneStack.pop();
            toPop.onPause();
            toPop.onDestroy();
        }
    }
}
