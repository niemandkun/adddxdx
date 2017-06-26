package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.infrastructure.ServiceLocator;
import tech.niemandkun.opengl.io.Window;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
    @Override public Collection<SystemInfo> getAllSystems() { return mAllSystems; }

    private final Collection<ActiveSystem> mActiveSystems;
    private final Collection<SystemInfo> mAllSystems;

    CustomSetting(ServiceLocator locator) {
        mScenario = new Scenario(this);
        mMaterialFactory = locator.get(MaterialFactory.class);
        mActiveSystems = locator.getAll(ActiveSystem.class);
        mWindow = locator.get(Window.class);

        mAllSystems = getSystems(locator.getAll(System.class));
    }

    private List<SystemInfo> getSystems(Collection<System> systems) {
        List<SystemInfo> allSystems = new ArrayList<>();

        for (System system : systems) {
            Type genericParameterType = getGenericInterfaceParameter(system.getClass());
            allSystems.add(new SystemInfo((Class) genericParameterType, system));
        }

        return allSystems;
    }

    private Type getGenericInterfaceParameter(Class<?> clazz) {
        ParameterizedType firstGenericInterface = (ParameterizedType) clazz.getGenericInterfaces()[0];
        return firstGenericInterface.getActualTypeArguments()[0];
    }
}
