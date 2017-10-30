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

import org.adddxdx.math.Vector2;
import org.lwjgl.glfw.*;

import java.time.Duration;
import java.util.*;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

class GlfwMouse implements Mouse {
    private final static int MAX_EVENTS_COUNT = 128;

    private final Set<ScrollListener> mScrollListeners;
    private final Set<ButtonPressListener> mButtonPressListeners;
    private final Set<ButtonReleaseListener> mButtonReleaseListeners;
    private final Set<MovementListener> mMovementListeners;
    private final Queue<GlfwMouseEvent> mEventQueue;
    private final Set<Integer> mPressedButtons;

    private Vector2 mPointerPosition;

    GlfwMouse() {
        mEventQueue = new ArrayDeque<>();
        mScrollListeners = new HashSet<>();
        mButtonPressListeners = new HashSet<>();
        mButtonReleaseListeners = new HashSet<>();
        mMovementListeners = new HashSet<>();
        mPressedButtons = new HashSet<>();
    }

    private void postEvent(GlfwMouseEvent event) {
        if (mEventQueue.size() >= MAX_EVENTS_COUNT) return;
        mEventQueue.add(event);
    }

    GLFWCursorPosCallback getCursorPosCallback() { return mCursorPosCallback; }
    GLFWMouseButtonCallback getMouseButtonCallback() { return mMouseButtonCallback; }
    GLFWScrollCallback getScrollCallback() { return mScrollCallback; }

    private final GLFWCursorPosCallback mCursorPosCallback = new GLFWCursorPosCallback() {
        @Override public void invoke(long window, double xpos, double ypos) {
            Vector2 pointerPosition = new Vector2((float) xpos, (float) ypos);
            Vector2 pointerMovement = mPointerPosition != null ? pointerPosition.sub(mPointerPosition) : Vector2.ZERO;
            mPointerPosition = pointerPosition;
            postEvent(GlfwMouseEvent.getMovementEvent(pointerPosition, pointerMovement));
        }
    };

    private final GLFWMouseButtonCallback mMouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override public void invoke(long window, int button, int action, int mods) {
            postEvent(GlfwMouseEvent.getButtonEvent(mPointerPosition, button, action));

            if (action == GLFW_PRESS) mPressedButtons.add(button);
            else if (action == GLFW_RELEASE) mPressedButtons.remove(button);
        }
    };

    private final GLFWScrollCallback mScrollCallback = new GLFWScrollCallback() {
        @Override public void invoke(long window, double xoffset, double yoffset) {
            postEvent(GlfwMouseEvent.getScrollEvent(mPointerPosition, new Vector2((float) xoffset, (float) yoffset)));
        }
    };

    void update() {
        while (!mEventQueue.isEmpty()) {
            GlfwMouseEvent event = mEventQueue.remove();
            switch (event.getAction()) {
                case GlfwMouseEvent.ACTION_MOVE:
                    for (MovementListener listener : mMovementListeners)
                        listener.onPointerMoved(this, event);
                    break;

                case GlfwMouseEvent.ACTION_PRESS:
                    for (ButtonPressListener listener : mButtonPressListeners)
                        listener.onButtonPressed(this, event);
                    break;

                case GlfwMouseEvent.ACTION_RELEASE:
                    for (ButtonReleaseListener listener : mButtonReleaseListeners)
                        listener.onButtonReleased(this, event);
                    break;

                case GlfwMouseEvent.ACTION_SCROLL:
                    for (ScrollListener listener : mScrollListeners)
                        listener.onWheelScrolled(this, event);
                    break;
            }
        }
    }

    @Override
    public boolean isButtonPressed(int button) {
        return mPressedButtons.contains(button);
    }

    @Override
    public Vector2 getPointerPosition() {
        return mPointerPosition;
    }

    @Override
    public void addScrollListener(ScrollListener listener) {
        mScrollListeners.add(listener);
    }

    @Override
    public void removeScrollListener(ScrollListener listener) {
        mScrollListeners.remove(listener);
    }

    @Override
    public void addButtonPressListener(ButtonPressListener listener) {
        mButtonPressListeners.add(listener);
    }

    @Override
    public void removeButtonPressListener(ButtonPressListener listener) {
        mButtonPressListeners.remove(listener);
    }

    @Override
    public void addButtonReleaseListener(ButtonReleaseListener listener) {
        mButtonReleaseListeners.add(listener);
    }

    @Override
    public void removeButtonReleaseListener(ButtonReleaseListener listener) {
        mButtonReleaseListeners.remove(listener);
    }

    @Override
    public void addMovementListener(MovementListener listener) {
        mMovementListeners.add(listener);
    }

    @Override
    public void removeMovementListener(MovementListener listener) {
        mMovementListeners.remove(listener);
    }
}
