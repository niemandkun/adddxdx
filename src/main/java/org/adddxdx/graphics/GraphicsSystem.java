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

import org.adddxdx.engine.ActiveSystem;
import org.adddxdx.engine.Setting;
import org.adddxdx.engine.Resources;
import org.adddxdx.io.Window;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class GraphicsSystem extends ActiveSystem<GraphicsSystem.Component> {
    private final Set<Renderable> mRenderables;
    private Camera mCamera;
    private Light mLight;
    private Fog mFog;

    private final GlRenderer mRenderer;

    public GraphicsSystem(Setting setting) {
        Window window = setting.get(Window.class);
        Resources resources = setting.get(Resources.class);
        mRenderables = new HashSet<>();
        mRenderer = new GlRenderer(new GlWindowRenderTarget(window), resources);
    }

    public void setFog(Fog fog) {
        mFog = fog;
    }

    public Fog getFog() {
        return mFog;
    }

    void setCamera(Camera camera) {
        mCamera = camera;
    }

    Camera getCamera() {
        return mCamera;
    }

    public void setLight(Light light) {
        mLight = light;
    }

    public Light getLight() {
        return mLight;
    }

    public void addRenderable(Renderable renderable) {
        mRenderables.add(renderable);
    }

    public void removeRenderable(Renderable renderable) {
        mRenderables.remove(renderable);
    }

    @Override
    public void register(Component component) {
        component.connect(this);
    }

    @Override
    public void unregister(Component component) {
        component.disconnect(this);
    }

    @Override
    public void update(Duration timeSinceLastUpdate) {
        RenderSettings settings = RenderSettings.empty().putLight(mLight).putFog(mFog);
        mRenderer.renderAll(mCamera, settings, mRenderables);
    }

    public abstract static class Component extends org.adddxdx.engine.Component {
        protected abstract void connect(GraphicsSystem system);
        protected abstract void disconnect(GraphicsSystem system);
    }
}
