package tech.niemandkun.opengl.engine;

import com.sun.istack.internal.NotNull;
import tech.niemandkun.opengl.math.Transform;

import java.util.*;

public class Actor {
    private final Map<Class<? extends Component>, Component> mComponents = new HashMap<>();
    public Collection<? extends Component> getComponents() { return mComponents.values(); }

    private final Transform mTransform = new Transform();
    public Transform getTransform() { return mTransform; }

    private Scene mScene;
    public Scene getScene() { return mScene; }
    void setScene(Scene scene) { mScene = scene; }

    public void addComponent(@NotNull Component component) {
        if (component.getActor() != null)
            throw new IllegalArgumentException("Trying to add a Component, but it has already been added to an Actor.");

        component.setActor(this);
        component.onCreate();
        mComponents.put(component.getClass(), component);
    }

    public Component getComponent(Class<? extends Component> componentClass) {
        return mComponents.get(componentClass);
    }

    public void removeComponent(Class<? extends Component> componentClass) {
        if (!mComponents.containsKey(componentClass)) return;

        Component component = mComponents.get(componentClass);
        component.onDestroy();
        component.setActor(null);
        mComponents.remove(componentClass);
    }

    public void onCreate() { }

    public void onDestroy() {
        for (Component component : mComponents.values())
            component.onDestroy();

        mComponents.clear();
    }
}
