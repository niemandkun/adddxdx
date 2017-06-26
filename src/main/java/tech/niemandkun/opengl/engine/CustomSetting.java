package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.infrastructure.ServiceLocator;
import tech.niemandkun.opengl.io.Window;

import java.lang.reflect.ParameterizedType;
import java.util.*;

class CustomSetting implements Setting {
    @Override public MaterialFactory getMaterialFactory() { return mMaterialFactory; }
    @Override public Scenario getScenario() { return mScenario; }
    @Override public Window getWindow() { return mWindow; }
    @Override public Clock getClock() { return null; }

    private final MaterialFactory mMaterialFactory;
    private final Scenario mScenario;
    private final Window mWindow;

    @Override public Collection<ActiveSystem> getActiveSystems() { return mActiveSystems; }
    @Override public Map<Class<? extends Component>, System> getAllSystems() { return mAllSystems; }

    private final Collection<ActiveSystem> mActiveSystems;
    private final Map<Class<? extends Component>, System> mAllSystems;

    CustomSetting(ServiceLocator locator) {
        mScenario = new Scenario(this);
        mMaterialFactory = locator.get(MaterialFactory.class);
        mActiveSystems = locator.getAll(ActiveSystem.class);
        mWindow = locator.get(Window.class);

        mAllSystems = getSystems(locator.getAll(System.class));
    }

    private Map<Class<? extends Component>, System> getSystems(Collection<System> systems) {
        Map<Class<? extends Component>, System> allSystems = new HashMap<>();

        for (System<?> system : systems) {
            // FIXME: add check before cast

            Class<? extends Component> genericClass =
                    (Class<? extends Component>)
                            ((ParameterizedType) system.getClass().getGenericInterfaces()[0])
                                    .getActualTypeArguments()[0];

            allSystems.put(genericClass, system);
        }

        return allSystems;
    }
}
