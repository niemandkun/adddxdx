package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.graphics.ShaderFactory;
import tech.niemandkun.opengl.io.Window;

import java.util.Collection;

public interface Setting {
    ShaderFactory getShaderFactory();
    Scenario getScenario();
    Window getWindow();
    Clock getClock();

    Collection<ActiveSystem> getActiveSystems();
    Collection<SystemInfo> getAllSystems();

    /* etc */

    static Setting from(ServiceLocator locator) {
        return new CustomSetting(locator);
    }
}
