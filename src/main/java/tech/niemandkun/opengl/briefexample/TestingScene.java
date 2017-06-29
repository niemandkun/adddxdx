package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Scene;

public class TestingScene extends Scene {
    @Override
    public void onCreate() {
        spawnActor(SkyFrame.class);
        spawnActor(Player.class);
        spawnActor(Walls.class);
        spawnActor(Cylinder.class);
        spawnActor(Triangles.class);
        spawnActor(TestingLight.class);
        spawnActor(DebugGui.class);
    }
}
