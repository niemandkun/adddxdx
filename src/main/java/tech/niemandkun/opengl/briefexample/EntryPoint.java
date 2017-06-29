package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.*;
import tech.niemandkun.opengl.graphics.*;
import tech.niemandkun.opengl.io.*;
import tech.niemandkun.opengl.math.Size;

public class EntryPoint {
    private static ServiceLocator createLocator() {
        Platform platform = Platform.builder()
                .onWindow().setResizable(false)
                .onVideoMode().setSize(new Size(1024, 768))
                .onContext().setMajorVersion(3).setMinorVersion(3)
                .onFramebuffer().setMultiSampling(4)
                .build();

        MaterialFactory materialFactory = new MaterialFactory();
        GraphicsSystem graphicsSystem = new GraphicsSystem(platform.getWindow(), materialFactory);

        ServiceLocator locator = new ServiceLocator();
        locator.registerSingleton(Window.class, platform.getWindow());
        locator.registerSingleton(InputSystem.class, platform.getInputSystem());
        locator.registerSingleton(MaterialFactory.class, materialFactory);
        locator.registerSingleton(GraphicsSystem.class, graphicsSystem);

        return locator;
    }

    public static void main(String[] args) {
        ServiceLocator locator = createLocator();
        Story.basedOn(Setting.from(locator)).reveal(TestingScene.class);
    }
}
