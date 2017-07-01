package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitivesFactory;
import tech.niemandkun.opengl.io.Window;

import java.util.Collection;

public interface Setting {
    PrimitivesFactory getPrimitivesFactory();
    MaterialFactory getMaterialFactory();
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
