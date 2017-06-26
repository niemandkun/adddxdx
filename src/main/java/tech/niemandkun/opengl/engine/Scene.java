package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.graphics.MaterialFactory;

import java.util.*;

public abstract class Scene implements FullLifecycle {

    /** Internal scene API */
    Collection<SystemInfo> getAllSystems() { return mSetting.getAllSystems(); }
    void setSetting(Setting setting) { mSetting = setting; }

    /** User available scene API */
    public MaterialFactory getMaterialFactory() { return mSetting.getMaterialFactory(); }
    public Scenario getScenario() { return mSetting.getScenario(); }
    public Collection<? extends Actor> getActors() { return mActors; }
    public Clock getClock() { return mSetting.getClock(); }

    private List<Actor> mActors;
    private Setting mSetting;

    public Scene() {
        mActors = new ArrayList<>();
    }

    /** Lifecycle methods */
    @Override public void onCreate() { }
    @Override public void onResume() { }
    @Override public void onMainLoop() { }
    @Override public void onPause() { }
    @Override public void onDestroy() { removeAllActors(); }

    private void removeAllActors() {
        for (Actor actor : mActors)
            actor.onDestroy();

        mActors.clear();
    }

    /** Actors management */
    public <T extends Actor> T spawnActor(Class<T> actorClass) {
        T actor;

        try {
            actor = actorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Exception has occurred while spawning Actor, " + e.getMessage());
        }

        actor.setScene(this);
        actor.onCreate();

        return actor;
    }

    public void removeActor(Actor actor) {
        if (actor.getScene() != this)
            throw new RuntimeException("Cannot remove Actor which belongs to another Scene.");

        actor.onDestroy();
        actor.setScene(null);
        mActors.remove(actor);
    }
}
