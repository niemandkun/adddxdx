package tech.niemandkun.opengl.io;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

class GlfwKeyboard implements EventQueueKeyboard, GLFWKeyCallbackI {
    private final static int MAX_EVENTS_COUNT = 32;

    private final Set<KeyPressListener> mKeyPressListeners;
    private final Set<KeyReleaseListener> mKeyReleaseListeners;
    private final Queue<GlfwKeyboardEvent> mEventQueue;
    private final Set<Integer> mPressedKeys;

    GlfwKeyboard() {
        mKeyReleaseListeners = new HashSet<>();
        mKeyPressListeners = new HashSet<>();
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

    @Override
    public void deliverEvents() {
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
    }
}
