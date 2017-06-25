package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.input.Keyboard;

public abstract class Scene implements Destroyable {
    private Scenario mScenario;

    Scene(Scenario stack) {
        mScenario = stack;
    }

    public Scenario getScenario() {
        return mScenario;
    }

    public Keyboard getKeyboard() {
        return null;
    }

    public Clock getClock() {
        return null;
    }

    public void onCreate() { }
    public void onResume() { }
    public void onPause() { }
    public void onDestroy() { }

    @Override
    public void destroy() {
        onDestroy();
    }
}
