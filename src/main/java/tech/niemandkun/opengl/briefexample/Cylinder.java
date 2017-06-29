package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.*;

import static tech.niemandkun.opengl.math.FMath.HALF_PI;

public class Cylinder extends Actor {
    @Override
    public void onCreate() {
        CylinderGenerator generator = new CylinderGenerator();
        Mesh mesh = new Mesh(generator.getVertices(), generator.getNormals(), generator.getColors());
        addComponent(new MeshSkin(mesh, getScene().getMaterialFactory().get(DefaultMaterial.class)));

        getTransform().scale(1, 1, 2);
        getTransform().rotate(HALF_PI, 0, 0);
        getTransform().translate(4, 1, 0);
    }
}
