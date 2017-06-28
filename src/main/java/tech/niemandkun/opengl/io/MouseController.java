package tech.niemandkun.opengl.io;

public abstract class MouseController extends InputSystem.Component implements Mouse.MovementListener {
    @Override
    void connect(Keyboard keyboard, Mouse mouse) {
        mouse.addMovementListener(this);
    }

    @Override
    void disconnect(Keyboard keyboard, Mouse mouse) {
        mouse.addMovementListener(this);
    }
}
