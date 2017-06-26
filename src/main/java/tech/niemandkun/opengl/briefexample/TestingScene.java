package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Scene;
import tech.niemandkun.opengl.graphics.RenderTarget;

public class TestingScene extends Scene {
    private Triangles mTriangles;

    @Override
    protected void onCreate() {
        mTriangles = new Triangles(getMaterialFactory());
    }

    @Override
    protected void onRender(RenderTarget target) {
        mTriangles.render(target);
    }

    @Override
    protected void onDestroy() {
        mTriangles.destroy();
    }
}
