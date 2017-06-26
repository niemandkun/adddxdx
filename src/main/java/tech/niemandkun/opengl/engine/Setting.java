package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.infrastructure.ServiceLocator;
import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.io.Window;
import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.graphics.RenderTarget;

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
