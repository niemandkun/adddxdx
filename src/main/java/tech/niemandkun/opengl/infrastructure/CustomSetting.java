package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.EventQueueKeyboard;
import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.io.Window;
import tech.niemandkun.opengl.shapes.MaterialFactory;
import tech.niemandkun.opengl.shapes.RenderTarget;

class CustomSetting implements Setting {
    @Override public MaterialFactory getMaterialFactory() { return mMaterialFactory; }
    @Override public RenderTarget getRenderTarget() { return mRenderTarget; }
    @Override public Keyboard getKeyboard() { return mKeyboard; }
    @Override public Scenario getScenario() { return mScenario; }
    @Override public Window getWindow() { return mWindow; }
    @Override public Clock getClock() { return null; }

    private final MaterialFactory mMaterialFactory;
    private final EventQueueKeyboard mKeyboard;
    private final RenderTarget mRenderTarget;
    private final Scenario mScenario;
    private final Window mWindow;

    CustomSetting(ServiceLocator locator) {
        mScenario = new Scenario(this);

        mMaterialFactory = locator.get(MaterialFactory.class);
        mRenderTarget = locator.get(RenderTarget.class);
        mKeyboard = locator.get(EventQueueKeyboard.class);
        mWindow = locator.get(Window.class);
    }
}
