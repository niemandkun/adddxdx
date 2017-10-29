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

import org.adddxdx.graphics.GraphicsSystem;
import org.adddxdx.math.Color;

public class Fog extends GraphicsSystem.Component implements org.adddxdx.graphics.Fog {
    private Color mColor = new Color(0xFFFFFFFF);
    private float mExtinction = 0.6f;
    private float mDensity = 0.03f;

    public void setColor(Color color) {
        mColor = color;
    }

    public void setExtinction(float extinction) {
        mExtinction = extinction;
    }

    public void setDensity(float density) {
        mDensity = density;
    }

    @Override
    public Color getColor() {
        return mColor;
    }

    @Override
    public float getExtinction() {
        return mExtinction;
    }

    @Override
    public float getDensity() {
        return mDensity;
    }

    @Override
    protected void connect(GraphicsSystem system) {
        if (system.getFog() == null) system.setFog(this);
    }

    @Override
    protected void disconnect(GraphicsSystem system) {
        if (this.equals(system.getFog())) system.setFog(null);
    }
}
