package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.io.Window;

import java.util.Collection;

public class Story implements Destroyable {
    private final Setting mSetting;

    public static Story basedOn(Setting setting) { return new Story(setting); }

    private Story(Setting setting) {
        mSetting = setting;
    }

    public void reveal(Class<? extends Scene> actFirst) {
        Scenario scenario = mSetting.getScenario();
        Window window = mSetting.getWindow();
        Scene currentAct;

        scenario.push(actFirst);

        Collection<ActiveSystem> activeSystems = mSetting.getActiveSystems();

        while (window.isOpen() && (currentAct = scenario.peekScene()) != null) {
            currentAct.onMainLoop();
            activeSystems.forEach(ActiveSystem::fixedUpdate);
            window.update();
        }

        destroy();
    }

    @Override
    public void destroy() {
        mSetting.getScenario().popAll();
        mSetting.getWindow().destroy();
        mSetting.getShaderFactory().destroy();
    }
}
