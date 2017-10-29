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
import org.adddxdx.graphics.support.materials.DefaultMaterial;
import org.adddxdx.graphics.support.primitives.PrimitiveType;
import org.adddxdx.graphics.support.primitives.PrimitivesFactory;
import org.adddxdx.graphics.support.textures.Texture;
import org.adddxdx.math.*;

public class Sprite extends GraphicsSystem.Component implements Renderable {

    private final Matrix4 mScaleMatrix;
    private final DefaultMaterial mMaterial;
    private final Mesh mMesh;

    private float mLastGoodSpriteRotation;

    // TODO: should not pass factory here. If I need to pass it here, something went wrong.
    public Sprite(Texture texture, MaterialFactory materialFactory, PrimitivesFactory primitivesFactory) {
        mMaterial = materialFactory.get(DefaultMaterial.class);
        mMaterial.setTexture(texture);

        float aspectRatio = (float) texture.getSize().getWidth() / texture.getSize().getHeight();
        mScaleMatrix = Matrix4.getScaleMatrix(aspectRatio, 1, 1);

        mMesh = primitivesFactory.create(PrimitiveType.QUAD);
    }

    // FIXME: gaps in shadows
    // maybe it's better to render a solid bounding box instead of the sprite when drawing the shadow
    @Override
    public void render(Renderer renderer, RenderSettings settings) {
        float spriteRotation = getSpriteRotation(settings);
        mLastGoodSpriteRotation = spriteRotation;
        Matrix4 modelMatrix = getModelMatrix(spriteRotation);
        renderer.render(mMesh.getVertexArray(), settings.putModelMatrix(modelMatrix).putMaterial(mMaterial));
    }

    private Matrix4 getModelMatrix(float spriteRotation) {
        Matrix4 rotationMatrix = Quaternion.fromEulerAngles(0, spriteRotation, 0).getRotationMatrix();

        return Matrix4.getTranslationMatrix(getActor().getTransform().getLocation())
                .cross(rotationMatrix)
                .cross(Matrix4.getScaleMatrix(getActor().getTransform().getScale()))
                .cross(mScaleMatrix);
    }

    private float getSpriteRotation(RenderSettings settings) {
        Vector3 viewLocation = settings.getView().getLocation();
        Vector3 spriteLocation = getActor().getTransform().getLocation();
        Vector3 directionToSprite = spriteLocation.sub(viewLocation);

        return directionToSprite.length2() == 0
                ? mLastGoodSpriteRotation
                : new Vector2(-directionToSprite.getZ(), -directionToSprite.getX()).angle();
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
