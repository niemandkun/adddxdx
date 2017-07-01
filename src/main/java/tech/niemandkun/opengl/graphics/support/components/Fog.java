package tech.niemandkun.opengl.graphics.support.components;

import tech.niemandkun.opengl.graphics.GraphicsSystem;
import tech.niemandkun.opengl.math.Color;

public class Fog extends GraphicsSystem.Component implements tech.niemandkun.opengl.graphics.Fog {
    private Color mColor = new Color(0x667F99FF);
    private float mExtinction = 0.6f;
    private float mDensity = 0.03f;

    void setColor(Color color) {
        mColor = color;
    }

    void setExtinction(float extinction) {
        mExtinction = extinction;
    }

    void setDensity(float density) {
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
