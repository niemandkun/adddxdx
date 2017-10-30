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

import org.adddxdx.fio.ResourceManager;
import org.adddxdx.fio.Image;
import org.adddxdx.fio.WavefrontObject;
import org.adddxdx.graphics.Material;
import org.adddxdx.graphics.MaterialFactory;
import org.adddxdx.graphics.Mesh;
import org.adddxdx.graphics.Shader;
import org.adddxdx.graphics.ShaderDescription;
import org.adddxdx.graphics.support.primitives.PrimitiveType;
import org.adddxdx.graphics.support.primitives.PrimitivesFactory;
import org.adddxdx.graphics.support.textures.Texture;

import java.io.File;
import java.net.URL;

class ResourcesImpl implements Resources, Destroyable {

    private ResourceManager<Texture> mTextureManager;
    private ResourceManager<Mesh> mMeshManager;
    private PrimitivesFactory mPrimitivesFactory;
    private MaterialFactory mMaterialFactory;

    ResourcesImpl(Setting setting) {
        mTextureManager = new ResourceManager<>(path -> new Texture(Image.load(open(path))));
        mMeshManager = new ResourceManager<>(path -> WavefrontObject.fromFile(open(path)).getMesh());
        mPrimitivesFactory = setting.get(PrimitivesFactory.class);
        mMaterialFactory = setting.get(MaterialFactory.class);
    }

    private File open(String filename) {
        URL resource = getClass().getClassLoader().getResource(filename);
        if (resource == null) {
            throw new ResourceNotFoundException(filename);
        }
        return new File(resource.getFile());
    }

    @Override
    public <TMaterial extends Material> TMaterial getMaterial(Class<TMaterial> material) {
        return mMaterialFactory.get(material);
    }

    @Override
    public Shader getShader(ShaderDescription shaderDescription) {
        return mMaterialFactory.getShader(shaderDescription);
    }

    @Override
    public Mesh getPrimitive(PrimitiveType primitiveType) {
        return mPrimitivesFactory.create(primitiveType);
    }

    @Override
    public Mesh getMesh(String path) {
        return mMeshManager.load(path);
    }

    @Override
    public Texture getTexture(String path) {
        return mTextureManager.load(path);
    }

    @Override
    public void destroy() {
        mTextureManager.destroy();
        mMeshManager.destroy();
        mMaterialFactory.destroy();
    }
}
