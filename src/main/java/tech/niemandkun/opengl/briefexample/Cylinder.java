package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.fio.Image;
import tech.niemandkun.opengl.graphics.Material;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.graphics.support.components.MeshSkin;
import tech.niemandkun.opengl.graphics.support.materials.CartoonMaterial;
import tech.niemandkun.opengl.graphics.support.primitives.PrimitiveType;
import tech.niemandkun.opengl.graphics.support.textures.Texture;

import java.io.File;

import static tech.niemandkun.opengl.math.FMath.HALF_PI;

public class Cylinder extends Actor {
    private Image mImage;

    @Override
    public void onCreate() {
        Mesh mesh = getScene().getPrimitivesFactory().create(PrimitiveType.CYLINDER);
        CartoonMaterial material = getScene().getMaterialFactory().get(CartoonMaterial.class);

        material.setTexture(new Texture(mImage = Image.load(open("test.bmp"))));
        addComponent(new MeshSkin(mesh, material));

        getTransform().scale(1, 1, 2);
        getTransform().rotate(HALF_PI, 0, 0);
    }

    private File open(String filename) {
        return new File(getClass().getClassLoader().getResource(filename).getFile());
    }

    @Override
    public void onDestroy() {
        mImage.destroy();
    }
}
