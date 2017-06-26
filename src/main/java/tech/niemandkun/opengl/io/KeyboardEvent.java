package tech.niemandkun.opengl.io;

public interface KeyboardEvent {
    int getScancode();
    int getKey();
    boolean isRepeated();
    boolean isShiftPressed();
    boolean isControlPressed();
    boolean isAltPressed();
}
