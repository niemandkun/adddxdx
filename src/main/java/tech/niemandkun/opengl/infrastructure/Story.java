package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.input.*;
import tech.niemandkun.opengl.io.output.Window;

class Story implements Destroyable {
    private final Scenario mScenario;
    private final EventQueueKeyboard mKeyboard;
    private final Window mWindow;

    static Story basedOn(Setting setting) { return new Story(setting); }

    private Story(Setting setting) {
        mScenario = setting.getScenario();
        mWindow = setting.getWindow();
        mKeyboard = mWindow.getKeyboard();
    }

    void reveal(Class<? extends Scene> actFirst) {
        mScenario.push(actFirst);

        while (mWindow.isOpen()) {
            Scene currentAct = mScenario.peekScene();

            if (currentAct == null)
                break;

            mKeyboard.deliverEvents();

            mWindow.clear();
            currentAct.onRender();
            mWindow.display();
        }
    }

    @Override
    public void destroy() {
        mWindow.destroy();
    }
}
