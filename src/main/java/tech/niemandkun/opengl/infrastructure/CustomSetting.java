package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.input.EventQueueKeyboard;
import tech.niemandkun.opengl.io.input.Keyboard;
import tech.niemandkun.opengl.io.output.Window;

class CustomSetting implements Setting {
    @Override public Keyboard getKeyboard() { return mKeyboard; }
    @Override public Scenario getScenario() { return mScenario; }
    @Override public Window getWindow() { return mWindow; }
    @Override public Clock getClock() { return null; }

    private final Scenario mScenario;
    private final EventQueueKeyboard mKeyboard;
    private final Window mWindow;

    CustomSetting(ServiceLocator locator) {
        mScenario = new Scenario(this);
        mWindow = locator.get(Window.class);
        mKeyboard = mWindow.getKeyboard();
    }
}
