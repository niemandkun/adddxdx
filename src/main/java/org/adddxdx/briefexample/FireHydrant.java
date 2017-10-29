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
import org.adddxdx.graphics.support.components.Sprite;
import org.adddxdx.graphics.support.textures.Texture;

import java.io.File;

public class FireHydrant extends Actor {
    private final static float SCALE = 0.4f;

    private Image mImage;

    @Override
    public void onCreate() {
        super.onCreate();
        mImage = Image.load(open("textures/hydrant.png"));
        addComponent(new Sprite(new Texture(mImage), getScene().getMaterialFactory(), getScene().getPrimitivesFactory()));
        getTransform().setScale(SCALE);
        getTransform().setLocation(0, SCALE, 0);
    }

    private File open(String filename) {
        return new File(getClass().getClassLoader().getResource(filename).getFile());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImage.destroy();
    }
}
