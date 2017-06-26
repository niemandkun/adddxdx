package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.shapes.Triangles;

class TestingScene extends Scene {
    private Triangles mTriangles;

    @Override
    public void onCreate() {
        mTriangles = new Triangles();

        getKeyboard().addKeyPressListener(mTriangles);
    }

    @Override
    public void onRender() {
        mTriangles.render();
    }

    @Override
    void onDestroy() {
        getKeyboard().removeKeyPressListener(mTriangles);
    }
}
