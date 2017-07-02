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

package org.adddxdx.graphics.support.textures;

import org.adddxdx.fio.Image;
import org.adddxdx.graphics.GlTexture;
import org.adddxdx.math.Size;

import static org.lwjgl.opengl.GL11.*;

public class Texture extends GlTexture {
    private final Image mImage;

    public Texture(Image image) {
        super(image.getSize());
        mImage = image;
    }

    @Override
    protected int getGlTextureFormat() {
        return GL_RGBA;
    }

    @Override
    protected int doInit(Size size) {
        int textureHandle = glGenTextures();
        int glTextureFormat = getGlTextureFormat();

        glBindTexture(GL_TEXTURE_2D, textureHandle);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0,
                glTextureFormat, size.getWidth(), size.getHeight(), 0,
                glTextureFormat, GL_UNSIGNED_BYTE, mImage.getImageBytes());

        return textureHandle;
    }
}
