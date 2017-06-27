package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.*;
import tech.niemandkun.opengl.graphics.GraphicsSystem;
import tech.niemandkun.opengl.graphics.ShaderFactory;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.Size;

public class TestingEngine implements Runnable {
    @Override
    public void run() {
        ServiceLocator locator = createLocator();
        Story.basedOn(Setting.from(locator)).reveal(TestingScene.class);
    }

    private static ServiceLocator createLocator() {
        Platform platform = Platform.builder()
                .onWindow().setResizable(false)
                .onVideoMode().setSize(new Size(1024, 768))
                .onContext().setMajorVersion(3).setMinorVersion(3)
                .onFramebuffer().setMultiSampling(4)
                .build();

        ShaderFactory shaderFactory = new ShaderFactory();
        GraphicsSystem graphicsSystem = new GraphicsSystem(platform.getWindow(), shaderFactory);

        ServiceLocator locator = new ServiceLocator();
        locator.registerSingleton(Window.class, platform.getWindow());
        locator.registerSingleton(InputSystem.class, platform.getInputSystem());
        locator.registerSingleton(ShaderFactory.class, shaderFactory);
        locator.registerSingleton(GraphicsSystem.class, graphicsSystem);

        return locator;
    }

    public static void main(String[] args) {
        new TestingEngine().run();
    }
}
