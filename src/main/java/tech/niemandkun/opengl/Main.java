package tech.niemandkun.opengl;

import tech.niemandkun.opengl.io.Window;

public class Main {
    public static void main(String[] args) {
        Window window = Window.builder()
                .onContext().setMajorVersion(3).setMinorVersion(3).and()
                .onFramebuffer().setMultiSampling(4).and()
                .build();

        Triangles triangles = new Triangles();

        while (window.isOpen()) {
            window.clear();
            triangles.render();
            window.display();
        }

        window.close();
    }
}
