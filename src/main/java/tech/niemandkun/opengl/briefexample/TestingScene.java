package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Scene;

public class TestingScene extends Scene {
    @Override
    public void onCreate() {
        spawnActor(Triangles.class);
        spawnActor(Player.class);
        spawnActor(Walls.class);
        spawnActor(TestingLight.class);
        spawnActor(Cylinder.class);
    }
}
