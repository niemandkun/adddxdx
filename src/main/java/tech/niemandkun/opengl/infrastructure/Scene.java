package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.input.Keyboard;

abstract class Scene {
    private Setting mSetting;

    void setSetting(Setting setting) {
        mSetting = setting;
    }

    Keyboard getKeyboard() { return mSetting.getKeyboard(); }
    Scenario getScenario() { return mSetting.getScenario(); }
    Clock getClock() { return mSetting.getClock(); }

    void onCreate() { }
    void onResume() { }
    void onRender() { }
    void onPause() { }
    void onDestroy() { }
}
