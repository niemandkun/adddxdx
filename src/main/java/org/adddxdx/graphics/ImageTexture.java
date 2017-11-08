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

package org.adddxdx.graphics;

import org.adddxdx.fio.Image;

import static org.lwjgl.opengl.GL11.*;

public class ImageTexture extends GlTexture {
    private final Image mImage;

    public ImageTexture(Image image) {
        this(image, WrapMode.REPEAT);
    }

    public ImageTexture(Image image, WrapMode wrapMode) {
        super(image.getSize(), wrapMode);
        mImage = image;
    }

    @Override
    protected int getGlTextureFormat() {
        return GL_RGBA;
    }

    @Override
    protected void onInit() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        int glTextureFormat = getGlTextureFormat();

        glTexImage2D(GL_TEXTURE_2D, 0,
                glTextureFormat, getWidth(), getHeight(), 0,
                glTextureFormat, GL_UNSIGNED_BYTE, mImage.getImageBytes());
    }
}
