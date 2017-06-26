package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.infrastructure.Scene;
import tech.niemandkun.opengl.shapes.RenderTarget;

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
