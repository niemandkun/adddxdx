package tech.niemandkun.opengl;

import tech.niemandkun.opengl.io.output.Window;
import tech.niemandkun.opengl.shapes.Triangles;

public class Main {
    public static void main(String[] args) {

        Window window = Window.builder()
                .onWindow().setResizable(false)
                .onContext().setMajorVersion(3).setMinorVersion(3)
                .onFramebuffer().setMultiSampling(4)
                .build();

        Triangles triangles = new Triangles();

        window.setKeyCallback(triangles);

        while (window.isOpen()) {
            window.clear();
            triangles.render();
            window.display();
        }

        triangles.destroy();
        window.destroy();
    }
}
