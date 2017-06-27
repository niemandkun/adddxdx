package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.math.Vector2;

public interface Mouse {
    interface ScrollListener {
        void onWheelScrolled(Mouse mouse, MouseEvent event);
    }

    void addScrollListener(ScrollListener listener);
    void removeScrollListener(ScrollListener listener);

    interface ButtonPressListener {
        void onButtonPressed(Mouse mouse, MouseEvent event);
    }

    void addButtonPressListener(ButtonPressListener listener);
    void removeButtonPressListener(ButtonPressListener listener);

    interface ButtonReleaseListener {
        void onButtonReleased(Mouse mouse, MouseEvent event);
    }

    void addButtonReleaseListener(ButtonReleaseListener listener);
    void removeButtonReleaseListener(ButtonReleaseListener listener);

    interface MovementListener {
        void onPointerMoved(Mouse mouse, MouseEvent event);
    }

    void addMovementListener(MovementListener listener);
    void removeMovementListener(MovementListener listener);

    boolean isButtonPressed(int button);
    Vector2 getPointerPosition();
}
