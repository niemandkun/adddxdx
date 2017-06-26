package tech.niemandkun.opengl.io;

public interface Keyboard {
    interface KeyPressListener {
        void onKeyPressed(Keyboard keyboard, KeyboardEvent event);
    }

    interface KeyReleaseListener {
        void onKeyReleased(Keyboard keyboard, KeyboardEvent event);
    }

    void addKeyPressListener(KeyPressListener listener);
    void removeKeyPressListener(KeyPressListener listener);

    void addKeyReleaseListener(KeyReleaseListener listener);
    void removeKeyReleasedListener(KeyReleaseListener listener);

    boolean isPressed(int keycode);
}
