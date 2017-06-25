package tech.niemandkun.opengl.io.output;

class WindowSettings {
    private final boolean mResizable;
    private final boolean mDecorated;
    private final boolean mFocused;
    private final boolean mAutoIconify;
    private final boolean mFloating;
    private final boolean mMaximized;
    private final boolean mFullscreen;

    WindowSettings(
            boolean resizable, boolean decorated, boolean focused, boolean autoIconify,
            boolean floating, boolean maximized, boolean fullscreen) {

        mResizable = resizable;
        mDecorated = decorated;
        mFocused = focused;
        mAutoIconify = autoIconify;
        mFloating = floating;
        mMaximized = maximized;
        mFullscreen = fullscreen;
    }

    boolean isResizable() {
        return mResizable;
    }

    boolean isDecorated() {
        return mDecorated;
    }

    boolean isFocused() {
        return mFocused;
    }

    boolean isAutoIconify() {
        return mAutoIconify;
    }

    boolean isFloating() {
        return mFloating;
    }

    boolean isMaximized() {
        return mMaximized;
    }

    boolean isFullscreen() {
        return mFullscreen;
    }
}
