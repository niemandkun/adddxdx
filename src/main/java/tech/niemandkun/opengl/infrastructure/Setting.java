package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.io.Window;
import tech.niemandkun.opengl.shapes.MaterialFactory;
import tech.niemandkun.opengl.shapes.RenderTarget;

public interface Setting {
    MaterialFactory getMaterialFactory();
    RenderTarget getRenderTarget();
    Keyboard getKeyboard();
    Scenario getScenario();
    Window getWindow();
    Clock getClock();

    /* etc */

    static Setting from(ServiceLocator locator) {
        return new CustomSetting(locator);
    }
}
