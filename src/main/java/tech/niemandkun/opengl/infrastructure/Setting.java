package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.input.Keyboard;
import tech.niemandkun.opengl.io.output.Window;

interface Setting {
    Keyboard getKeyboard();
    Scenario getScenario();
    Window getWindow();
    Clock getClock();

    /* etc */

    static Setting from(ServiceLocator locator) {
        return new CustomSetting(locator);
    }
}
