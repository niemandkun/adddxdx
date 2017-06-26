package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Scene;

public class TestingScene extends Scene {
    @Override
    protected void onCreate() {
        spawnActor(Triangles.class);
        spawnActor(Player.class);
    }
}
