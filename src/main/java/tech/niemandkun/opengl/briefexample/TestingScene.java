package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Scene;
import tech.niemandkun.opengl.graphics.RenderTarget;

public class TestingScene extends Scene {
    private Triangles mTriangles;

    @Override
    protected void onCreate() {
        mTriangles = spawnActor(Triangles.class);

        spawnActor(Player.class);
    }

    @Override
    protected void onRender(RenderTarget target) {
        getRenderTarget().clear();
        mTriangles.render(target);
    }
}
