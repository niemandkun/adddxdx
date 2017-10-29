/*
 * Copyright (C) 2017 Poroshin Ivan
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.briefexample;

import org.adddxdx.engine.Actor;
import org.adddxdx.fio.Image;
import org.adddxdx.fio.WavefrontObject;
import org.adddxdx.graphics.Mesh;
import org.adddxdx.graphics.support.components.MeshSkin;
import org.adddxdx.graphics.support.materials.DefaultMaterial;
import org.adddxdx.graphics.support.textures.Texture;
import org.adddxdx.math.Color;
import org.adddxdx.math.FMath;

import java.io.File;

public class House extends Actor {

    private Image mImage;

    @Override
    public void onCreate() {
        super.onCreate();

        Mesh mesh = WavefrontObject.fromFile(open("models/house.obj")).getMesh();

        DefaultMaterial material = getScene().getMaterialFactory().get(DefaultMaterial.class);

        mImage = Image.load(open("textures/house.tga"));
        material.setTexture(new Texture(mImage));

        addComponent(new MeshSkin(mesh, material));

        getTransform().rotate(FMath.HALF_PI, FMath.PI, 0);
        getTransform().translate(-5, 0, 3);
    }

    private File open(String filename) {
        return new File(getClass().getClassLoader().getResource(filename).getFile());
    }

    @Override
    public void onDestroy() {
        mImage.destroy();
    }
}
