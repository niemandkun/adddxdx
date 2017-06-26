package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.graphics.MaterialFactory;
import tech.niemandkun.opengl.graphics.RenderTarget;

public abstract class Scene {
    private Setting mSetting;

    void setSetting(Setting setting) {
        mSetting = setting;
    }

    public Scene() { }

    protected MaterialFactory getMaterialFactory() { return mSetting.getMaterialFactory(); }
    protected Keyboard getKeyboard() { return mSetting.getKeyboard(); }
    protected Scenario getScenario() { return mSetting.getScenario(); }
    protected Clock getClock() { return mSetting.getClock(); }

    protected void onCreate() { }
    protected void onResume() { }
    protected void onRender(RenderTarget renderTarget) { }
    protected void onPause() { }
    protected void onDestroy() { }
}
