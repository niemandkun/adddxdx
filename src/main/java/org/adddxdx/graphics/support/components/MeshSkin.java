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

package org.adddxdx.graphics.support.components;

import org.adddxdx.graphics.*;

public class MeshSkin extends GraphicsSystem.Component implements Renderable {
    private final Material mMaterial;
    private final Mesh mMesh;

    private boolean mCastShadows;

    public MeshSkin(Mesh mesh) {
        this(mesh, null);
    }

    public MeshSkin(Mesh mesh, Material material) {
        mCastShadows = true;
        mMaterial = material;
        mMesh = mesh;
    }

    public boolean isCastingShadows() {
        return mCastShadows;
    }

    public void setCastShadows(boolean castShadows) {
        mCastShadows = castShadows;
    }

    @Override
    public void render(Renderer renderer, RenderSettings settings) {
        if (!mCastShadows && settings.isShadowPass()) return;

        renderer.render(mMesh.getVertexArray(), settings
                .putModelMatrix(getActor().getTransform().getMatrix())
                .putMaterial(mMaterial));
    }

    @Override
    protected void connect(GraphicsSystem system) {
        system.addRenderable(this);
    }

    @Override
    protected void disconnect(GraphicsSystem system) {
        system.removeRenderable(this);
    }
}
