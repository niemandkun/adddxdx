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

import org.adddxdx.engine.Destroyable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MaterialFactory implements Destroyable {
    private final static String FRAGMENT_SHADER_EXT = ".frag";
    private final static String VERTEX_SHADER_EXT = ".vert";
    private final static String SHADERS_DIR = "shaders/";

    private final ShaderCompiler mShaderCompiler;

    private final Map<ShaderDescription, Shader> mShaders;

    public MaterialFactory() {
        mShaderCompiler = Shader.getCompiler();
        mShaders = new HashMap<>();
    }

    public <TMaterial extends Material> TMaterial get(Class<TMaterial> material) {
        TMaterial instance = instantiateMaterial(material);
        instance.setShader(getShader(instance.getShaderDescription()));
        return instance;
    }

    Shader getShader(ShaderDescription description) {
        Shader shader = mShaders.get(description);

        if (shader == null) {
            shader = buildShader(description);
            mShaders.put(description, shader);
        }

        return shader;
    }

    private <TMaterial extends Material> TMaterial instantiateMaterial(Class<TMaterial> material) {
        try {
            return material.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate material: " + material.getClass() + ", " + e.getMessage());
        }
    }

    private Shader buildShader(ShaderDescription desc) {
        try {

            return mShaderCompiler
                .setFragmentShader(open(SHADERS_DIR + desc.getFragmentShaderName() + FRAGMENT_SHADER_EXT))
                .setVertexShader(open(SHADERS_DIR + desc.getVertexShaderName() + VERTEX_SHADER_EXT))
                .compile();

        } catch (ShaderCompileException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private File open(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }

    @Override
    public void destroy() {
        mShaders.values().forEach(Shader::destroy);
    }
}
