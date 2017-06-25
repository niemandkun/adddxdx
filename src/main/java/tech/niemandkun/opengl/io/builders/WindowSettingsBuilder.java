package tech.niemandkun.opengl.io.builders;

import tech.niemandkun.opengl.io.WindowSettings;

public class WindowSettingsBuilder {
    WindowSettings mWindowSettings;

    WindowSettingsBuilder() {
        mWindowSettings = new WindowSettings(true, true, true, true, false, false, false);
    }

    private WindowSettingsBuilder setResizable(boolean isResizable) {
        mWindowSettings = new WindowSettings()
    }
}
