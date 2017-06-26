package tech.niemandkun.opengl.engine;

import java.util.Collection;

public abstract class Component implements ShortLifecycle{
    private Actor mActor;
    public Actor getActor() { return mActor; }
    void setActor(Actor actor) { mActor = actor; }

    public Scene getScene() { return mActor.getScene(); }

    @Override
    public void onCreate() {
        Collection<SystemInfo> systems = getScene().getAllSystems();

        for (SystemInfo system : systems)
            if (system.isAcceptingComponent(getClass()))
                system.getSystem().register(this);
    }

    @Override
    public void onDestroy() {
        Collection<SystemInfo> systems = getScene().getAllSystems();

        for (SystemInfo system : systems)
            if (system.isAcceptingComponent(getClass()))
                system.getSystem().unregister(this);
    }
}
