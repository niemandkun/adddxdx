package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.*;
import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.io.InputSystem;
import tech.niemandkun.opengl.io.Window;

public class TestingEngine implements Runnable {
    @Override
    public void run() {
        ServiceLocator locator = createLocator();
        Story.basedOn(Setting.from(locator)).reveal(TestingScene.class);
    }

    private static ServiceLocator createLocator() {
        Window window = Window.builder()
                .onWindow().setResizable(false)
                .onContext().setMajorVersion(3).setMinorVersion(3)
                .onFramebuffer().setMultiSampling(4)
                .build();

        ServiceLocator locator = new ServiceLocator();
        locator.registerSingleton(Window.class, window);
        locator.registerSingleton(MaterialFactory.class, new MaterialFactory());
        locator.registerSingleton(GraphicsSystem.class, new GraphicsSystem(new WindowRenderTarget(window)));
        locator.registerSingleton(InputSystem.class, new InputSystem(window.getKeyboard()));

        return locator;
    }

    public static void main(String[] args) {
        new TestingEngine().run();
    }
}
