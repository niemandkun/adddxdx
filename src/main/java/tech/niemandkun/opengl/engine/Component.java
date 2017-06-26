package tech.niemandkun.opengl.engine;

import java.util.Map;

public abstract class Component {
    private Actor mActor;
    public Actor getActor() { return mActor; }
    void setActor(Actor actor) { mActor = actor; }

    public Scene getScene() { return mActor.getScene(); }

    protected void onCreate() {
        Map<Class<? extends Component>, System> systems = getScene().getAllSystems();

        for (Class<? extends Component> clazz : systems.keySet())
            if (clazz.isAssignableFrom(getClass()))
                systems.get(clazz).register(this);
    }

    protected void onDestroy() {
        Map<Class<? extends Component>, System> systems = getScene().getAllSystems();

        for (Class<? extends Component> clazz : systems.keySet())
            if (clazz.isAssignableFrom(getClass()))
                systems.get(clazz).unregister(this);
    }
}
