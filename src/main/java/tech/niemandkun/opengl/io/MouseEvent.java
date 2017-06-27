package tech.niemandkun.opengl.io;

import tech.niemandkun.opengl.math.Vector2;

public interface MouseEvent {
    Vector2 getPointerPosition();
    Vector2 getPointerMovement();
    Vector2 getWheelMovement();
    int getButton();
}
