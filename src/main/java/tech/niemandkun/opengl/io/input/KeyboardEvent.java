package tech.niemandkun.opengl.io.input;

public interface KeyboardEvent {
    int getScancode();
    int getKey();
    boolean isRepeated();
    boolean isShiftPressed();
    boolean isControlPressed();
    boolean isAltPressed();
}
