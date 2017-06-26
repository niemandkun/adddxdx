package tech.niemandkun.opengl.infrastructure;

import tech.niemandkun.opengl.io.output.Window;

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

        Story.basedOn(Setting.from(locator)).reveal(TestingScene.class);
    }
}
