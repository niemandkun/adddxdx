package tech.niemandkun.opengl.engine;

public abstract class Component {
    private Actor mActor;
    public Actor getActor() { return mActor; }
    void setActor(Actor actor) { mActor = actor; }

    public Scene getScene() { return mActor.getScene(); }

    protected void onCreate() { }
    protected void onDestroy() { }
}
