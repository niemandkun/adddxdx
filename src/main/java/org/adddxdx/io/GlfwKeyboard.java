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

package org.adddxdx.io;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

class GlfwKeyboard implements Keyboard, GLFWKeyCallbackI {
    private final static int MAX_EVENTS_COUNT = 128;

    private final Set<KeyPressListener> mKeyPressListeners;
    private final Set<KeyReleaseListener> mKeyReleaseListeners;
    private final Queue<GlfwKeyboardEvent> mEventQueue;
    private final Set<Observer> mKeyboardObservers;
    private final Set<Integer> mPressedKeys;

    GlfwKeyboard() {
        mKeyReleaseListeners = new HashSet<>();
        mKeyPressListeners = new HashSet<>();
        mKeyboardObservers = new HashSet<>();
        mEventQueue = new ArrayDeque<>();
        mPressedKeys = new HashSet<>();
    }

    @Override
    public void addKeyPressListener(KeyPressListener listener) {
        mKeyPressListeners.add(listener);
    }

    @Override
    public void removeKeyPressListener(KeyPressListener listener) {
        mKeyPressListeners.remove(listener);
    }

    @Override
    public void addKeyReleaseListener(KeyReleaseListener listener) {
        mKeyReleaseListeners.add(listener);
    }

    @Override
    public void removeKeyReleasedListener(KeyReleaseListener listener) {
        mKeyReleaseListeners.remove(listener);
    }

    @Override
    public void addObserver(Observer observer) {
        mKeyboardObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        mKeyboardObservers.add(observer);
    }

    @Override
    public boolean isPressed(int keycode) {
        return mPressedKeys.contains(keycode);
    }

    @Override
    public void invoke(long window, int keycode, int scancode, int action, int modifiers) {
        if (mEventQueue.size() >= MAX_EVENTS_COUNT) return;

        boolean isRepeated = action == GLFW_REPEAT;
        mEventQueue.add(new GlfwKeyboardEvent(action, keycode, scancode, modifiers, isRepeated));

        if (action == GLFW_PRESS) mPressedKeys.add(keycode);
        if (action == GLFW_RELEASE) mPressedKeys.remove(keycode);
    }

    void update() {
        while (!mEventQueue.isEmpty()) {
            GlfwKeyboardEvent event = mEventQueue.remove();

            if (event.getAction() == GLFW_RELEASE) {
                for (KeyReleaseListener listener : mKeyReleaseListeners)
                    listener.onKeyReleased(this, event);
            } else {
                for (KeyPressListener listener : mKeyPressListeners)
                    listener.onKeyPressed(this, event);
            }
        }

        for (Observer observer : mKeyboardObservers)
            observer.checkKeyboardState(this);
    }
}
