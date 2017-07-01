package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Material;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.components.MeshSkin;
import tech.niemandkun.opengl.graphics.support.materials.CartoonMaterial;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitiveType;

import static tech.niemandkun.opengl.math.FMath.HALF_PI;

public class Cylinder extends Actor {
    @Override
    public void onCreate() {
        Mesh mesh = getScene().getPrimitivesFactory().create(PrimitiveType.CYLINDER);
        Material material = getScene().getMaterialFactory().get(CartoonMaterial.class);
        addComponent(new MeshSkin(mesh, material));

        getTransform().scale(1, 1, 2);
        getTransform().rotate(HALF_PI, 0, 0);
    }
}
