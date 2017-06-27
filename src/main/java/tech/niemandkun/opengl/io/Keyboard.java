package tech.niemandkun.opengl.io;

public interface Keyboard {
    interface KeyPressListener {
        void onKeyPressed(Keyboard keyboard, KeyboardEvent event);
    }

    void addKeyPressListener(KeyPressListener listener);
    void removeKeyPressListener(KeyPressListener listener);

    interface KeyReleaseListener {
        void onKeyReleased(Keyboard keyboard, KeyboardEvent event);
    }

    void addKeyReleaseListener(KeyReleaseListener listener);
    void removeKeyReleasedListener(KeyReleaseListener listener);

    boolean isPressed(int keycode);
}
