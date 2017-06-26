package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.infrastructure.Destroyable;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.graphics.RenderTarget;

public class Story implements Destroyable {
    private final Setting mSetting;

    public static Story basedOn(Setting setting) { return new Story(setting); }

    private Story(Setting setting) {
        mSetting = setting;
    }

    public void reveal(Class<? extends Scene> actFirst) {
        Window window = mSetting.getWindow();
        Scenario scenario = mSetting.getScenario();
        EventQueueKeyboard keyboard = window.getKeyboard();
        RenderTarget renderTarget = mSetting.getRenderTarget();

        scenario.push(actFirst);

        while (window.isOpen()) {
            Scene currentAct = scenario.peekScene();

            if (currentAct == null)
                break;

            keyboard.deliverEvents();
            currentAct.onRender(renderTarget);
            window.update();
        }

        destroy();
    }

    @Override
    public void destroy() {
        mSetting.getScenario().popAll();
        mSetting.getWindow().destroy();
        mSetting.getMaterialFactory().destroy();
    }
}
