package tech.niemandkun.opengl.engine;

interface FullLifecycle extends ShortLifecycle {
    void onResume();
    void onMainLoop();
    void onPause();
}
