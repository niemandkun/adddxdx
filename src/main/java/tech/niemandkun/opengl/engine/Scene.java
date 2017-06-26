package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.graphics.RenderTarget;
import tech.niemandkun.opengl.io.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private List<Actor> mActors;
    private Setting mSetting;

    void setSetting(Setting setting) {
        mSetting = setting;
    }

    public List<? extends Actor> getActors() {
        return mActors;
    }

    public Scene() {
        mActors = new ArrayList<>();
    }

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

    public MaterialFactory getMaterialFactory() { return mSetting.getMaterialFactory(); }
    public RenderTarget getRenderTarget() { return mSetting.getRenderTarget(); }
    public Keyboard getKeyboard() { return mSetting.getKeyboard(); }
    public Scenario getScenario() { return mSetting.getScenario(); }
    public Window getWindow() { return mSetting.getWindow(); }
    public Clock getClock() { return mSetting.getClock(); }

    protected void onCreate() { }

    protected void onResume() { }

    protected void onRender(RenderTarget renderTarget) { }

    protected void onPause() { }

    protected void onDestroy() {
        for (Actor actor : mActors)
            actor.onDestroy();

        mActors.clear();
    }
}
