package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.components.MeshSkin;
import tech.niemandkun.opengl.graphics.support.materials.SkyMaterial;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitiveType;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitivesFactory;

public class SkyFrame extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();
        Mesh mesh = getScene().getPrimitivesFactory().create(PrimitiveType.QUAD);
        addComponent(new MeshSkin(mesh, getScene().getMaterialFactory().get(SkyMaterial.class)));
    }
}
