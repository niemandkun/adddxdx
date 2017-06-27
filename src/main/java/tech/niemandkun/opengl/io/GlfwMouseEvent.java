package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.math.Vector2;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

class GlfwMouseEvent implements MouseEvent {
    final static int ACTION_RELEASE = GLFW_RELEASE;
    final static int ACTION_PRESS = GLFW_PRESS;
    final static int ACTION_MOVE = 2;
    final static int ACTION_SCROLL = 3;

    private final Vector2 mPointerPosition;
    private final Vector2 mPointerMovement;
    private final Vector2 mWheelMovement;
    private final int mButton;
    private final int mAction;

    @Override public Vector2 getPointerPosition() { return mPointerPosition; }
    @Override public Vector2 getPointerMovement() { return mPointerMovement; }
    @Override public Vector2 getWheelMovement() { return mWheelMovement; }
    @Override public int getButton() { return mButton; }

    int getAction() { return mAction; }

    static GlfwMouseEvent getMovementEvent(Vector2 pointerPosition, Vector2 pointerMovement) {
        return new GlfwMouseEvent(pointerPosition, pointerMovement, Vector2.ZERO, MouseButton.UNKNOWN, ACTION_MOVE);
    }

    static GlfwMouseEvent getScrollEvent(Vector2 pointerPosition, Vector2 wheelMovement) {
        return new GlfwMouseEvent(pointerPosition, Vector2.ZERO, wheelMovement, MouseButton.UNKNOWN, ACTION_SCROLL);
    }

    static GlfwMouseEvent getButtonEvent(Vector2 pointerPosition, int button, int action) {
        return new GlfwMouseEvent(pointerPosition, Vector2.ZERO, Vector2.ZERO, button, action);
    }

    private GlfwMouseEvent(Vector2 pointerPosition, Vector2 pointerMovement,
                           Vector2 wheelMovement, int button, int action) {

        mPointerPosition = pointerPosition;
        mPointerMovement = pointerMovement;
        mWheelMovement = wheelMovement;
        mButton = button;
        mAction = action;
    }
}
