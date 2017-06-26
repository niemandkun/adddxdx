package tech.niemandkun.opengl.engine;

import tech.niemandkun.opengl.infrastructure.*;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.shapes.*;

public class TestingEngine implements Runnable {
    @Override
    public void run() {
        Window window = Window.builder()
                .onWindow().setResizable(false)
                .onContext().setMajorVersion(3).setMinorVersion(3)
                .onFramebuffer().setMultiSampling(4)
                .build();

        ServiceLocator locator = new ServiceLocator();
        locator.registerSingleton(Window.class, window);
        locator.registerSingleton(EventQueueKeyboard.class, window.getKeyboard());
        locator.registerSingleton(RenderTarget.class, new GlRenderTarget());
        locator.registerSingleton(MaterialFactory.class, new MaterialFactory());

        Story.basedOn(Setting.from(locator)).reveal(TestingScene.class);
    }
}
