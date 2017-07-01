package tech.niemandkun.opengl.graphics.support.components;

import tech.niemandkun.opengl.graphics.GraphicsSystem;
import tech.niemandkun.opengl.graphics.Light;
import tech.niemandkun.opengl.math.*;

import static tech.niemandkun.opengl.math.FMath.PI;

abstract class BaseLight extends GraphicsSystem.Component implements Light {
    private Color mColor = Color.WHITE;
    private float mAmbientIntensity = 0.25f;

    @Override
    public final void connect(GraphicsSystem system) {
        if (system.getLight() == null) system.setLight(this);
    }

    @Override
    public final void disconnect(GraphicsSystem system) {
        if (this.equals(system.getLight())) system.setLight(null);
    }

    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 rotationFix = Matrix4.getRotationMatrix(0, PI, 0);
        return viewMatrix.cross(rotationFix).inverse();
    }

    public Vector3 getDirection() {
        return getActor().getTransform().getViewDirection();
    }

    @Override
    public Color getColor() {
        return mColor;
    }

    @Override
    public float getAmbientIntensity() {
        return mAmbientIntensity;
    }

    void setColor(Color color) {
        mColor = color;
    }

    void setAmbientIntensity(float ambientIntensity) {
        mAmbientIntensity = ambientIntensity;
    }
}
