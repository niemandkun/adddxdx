package tech.niemandkun.opengl.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeviceLocator {
    private final Map<Class, Supplier> mLocator;

    public DeviceLocator() {
        mLocator = new HashMap<>();
    }

    public <T> void registerFactory(Class<T> clazz, Supplier<T> factory) {
        mLocator.put(clazz, factory);
    }

    public <T> void registerSingleton(Class<T> clazz, T singleton) {
        mLocator.put(clazz, () -> singleton);
    }

    public <T> T get(Class<T> clazz) {
        if (mLocator.containsKey(clazz))
            return (T) mLocator.get(clazz).get();

        for (Class inLocator : mLocator.keySet()) {
            if (clazz.isAssignableFrom(inLocator))
                return (T) mLocator.get(inLocator).get();
        }

        throw new IllegalArgumentException("Class " + clazz + " is not found in locator.");
    }
}
