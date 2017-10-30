package org.adddxdx.fio;

import org.adddxdx.engine.Destroyable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ResourceManager<TResource> implements Destroyable {
    private final Map<String, TResource> mResourcesCache;
    private final Function<String, TResource> mOpenFunc;

    public ResourceManager(Function<String, TResource> openFunc) {
        mResourcesCache = new HashMap<>();
        mOpenFunc = openFunc;
    }

    public TResource load(String path) {
        TResource resource = mResourcesCache.get(path);
        if (resource == null) {
            mResourcesCache.put(path, resource = mOpenFunc.apply(path));
        }
        return resource;
    }

    @Override
    public void destroy() {
        for (TResource resource : mResourcesCache.values()) {
            if (resource instanceof Destroyable) {
                ((Destroyable) resource).destroy();
            }
        }
    }
}
