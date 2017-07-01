package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.fio.Image;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.components.MeshSkin;
import tech.niemandkun.opengl.graphics.support.materials.CartoonMaterial;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitiveType;
import tech.niemandkun.opengl.graphics.support.textures.Texture;
import tech.niemandkun.opengl.math.FMath;

import java.io.File;

public class PieceOfGlass extends Actor {
    private Image mImage;

    @Override
    public void onCreate() {
        super.onCreate();

        Mesh mesh = getScene().getPrimitivesFactory().create(PrimitiveType.QUAD);

        CartoonMaterial material = getScene().getMaterialFactory().get(CartoonMaterial.class);
        material.setTexture(new Texture(mImage = Image.load(open("textures/glass.png"))));

        addComponent(new MeshSkin(mesh, material));
        getComponent(MeshSkin.class).setCastShadows(false);

        getTransform().rotate(0, FMath.PI, 0);
        getTransform().translate(0, 1, 0);
    }

    private File open(String filename) {
        return new File(getClass().getClassLoader().getResource(filename).getFile());
    }

    @Override
    public void onDestroy() {
        mImage.destroy();
    }
}
