package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.MeshRenderer;

public class Cylinder extends Actor {
    @Override
    public void onCreate() {
        CylinderGenerator generator = new CylinderGenerator();
        Mesh mesh = new Mesh(generator.getVertices(), generator.getNormals(), generator.getColors());
        addComponent(new MeshRenderer(mesh, getScene().getMaterialFactory().getDefaultMaterial()));

        getTransform().scale(1, 1, 2);
        getTransform().rotate((float) Math.PI / 2, 0, 0);
        getTransform().translate(4, 1, 0);
    }
}
