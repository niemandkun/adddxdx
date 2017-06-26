package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.infrastructure.ServiceLocator;
import tech.niemandkun.opengl.io.Window;

import java.util.Collection;
import java.util.Map;

public interface Setting {
    MaterialFactory getMaterialFactory();
    Scenario getScenario();
    Window getWindow();
    Clock getClock();

    Collection<ActiveSystem> getActiveSystems();
    Map<Class<? extends  Component>, System> getAllSystems();

    /* etc */

    static Setting from(ServiceLocator locator) {
        return new CustomSetting(locator);
    }
}
