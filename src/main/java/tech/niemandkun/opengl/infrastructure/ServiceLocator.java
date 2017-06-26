package tech.niemandkun.opengl.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ServiceLocator {
    private final Map<Class, Supplier> mLocator;

    public ServiceLocator() {
        mLocator = new HashMap<>();
    }

    public <TService> void registerFactory(Class<TService> clazz, Supplier<TService> factory) {
        mLocator.put(clazz, factory);
    }

    public <TService> void registerSingleton(Class<TService> clazz, TService singleton) {
        mLocator.put(clazz, () -> singleton);
    }

    public <TService> TService get(Class<TService> clazz) {
        if (mLocator.containsKey(clazz))
            return (TService) mLocator.get(clazz).get();

        for (Class inLocator : mLocator.keySet()) {
            if (clazz.isAssignableFrom(inLocator))
                return (TService) mLocator.get(inLocator).get();
        }

        throw new IllegalArgumentException("Class " + clazz + " is not found in locator.");
    }
}
