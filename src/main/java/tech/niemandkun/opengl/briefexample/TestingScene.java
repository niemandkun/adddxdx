package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Scene;

public class TestingScene extends Scene {
    @Override
    public void onCreate() {
        spawnActor(SkyFrame.class);
        spawnActor(Player.class);
        spawnActor(Walls.class);
        spawnActor(Cylinder.class).getTransform().translate(5, 1, 0);
        spawnActor(Cylinder.class).getTransform().translate(5, 1, 5);
        spawnActor(Cylinder.class).getTransform().translate(0, 1, 5);
        spawnActor(Cylinder.class).getTransform().translate(-1, 1, -3);
        spawnActor(PieceOfGlass.class).getTransform().translate(2.5f, 0, 0);
        spawnActor(Triangles.class);
        spawnActor(TestingLight.class);
        spawnActor(DebugGui.class);
    }
}
