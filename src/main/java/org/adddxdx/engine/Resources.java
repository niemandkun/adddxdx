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

package org.adddxdx.engine;

import org.adddxdx.graphics.Material;
import org.adddxdx.graphics.Mesh;
import org.adddxdx.graphics.Shader;
import org.adddxdx.graphics.ShaderDescription;
import org.adddxdx.graphics.support.primitives.PrimitiveType;
import org.adddxdx.graphics.support.textures.Texture;

public interface Resources {
    <TMaterial extends Material> TMaterial getMaterial(Class<TMaterial> clazz);
    Shader getShader(ShaderDescription shaderDescription);
    Mesh getPrimitive(PrimitiveType primitiveType);
    Mesh getMesh(String path);
    Texture getTexture(String path);
}