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

public abstract class TexturedMaterial extends Material {
    private final static String UNIFORM_TEXTURE = "mainTexture";
    private final static int TEXTURE_UNIT_TO_USE = 2;

    private GlTexture mTexture;

    public TexturedMaterial() {
        mTexture = new StubTexture();
    }

    public void setTexture(GlTexture texture) {
        mTexture = texture;
    }

    @Override
    public void setupShader(RenderSettings settings, Shader shader) {
        if (mTexture != null) {
            if (!mTexture.isInitialized()) mTexture.init();
            shader.setUniform(UNIFORM_TEXTURE, mTexture.bind(TEXTURE_UNIT_TO_USE));
        }
    }
}
