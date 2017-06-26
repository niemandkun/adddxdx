package tech.niemandkun.opengl.io;

import static org.lwjgl.glfw.GLFW.GLFW_MOD_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;

class GlfwKeyboardEvent implements KeyboardEvent {
    private final int mAction;
    private final int mKey;
    private final int mScancode;
    private final int mModifiers;
    private final boolean mRepeated;

    GlfwKeyboardEvent(int action, int key, int scancode, int modifiers, boolean isRepeated) {
        mAction = action;
        mKey = key;
        mScancode = scancode;
        mModifiers = modifiers;
        mRepeated = isRepeated;
    }

    int getAction() {
        return mAction;
    }

    @Override
    public int getScancode() {
        return mScancode;
    }

    @Override
    public int getKey() {
        return mKey;
    }

    @Override
    public boolean isRepeated() {
        return mRepeated;
    }

    @Override
    public boolean isShiftPressed() {
        return (mModifiers & GLFW_MOD_SHIFT) != 0;
    }

    @Override
    public boolean isControlPressed() {
        return (mModifiers & GLFW_MOD_CONTROL) != 0;
    }

    @Override
    public boolean isAltPressed() {
        return (mModifiers & GLFW_MOD_ALT) != 0;
    }
}
