package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.*;

public abstract class Light extends GraphicsSystem.Component {
    @Override
    public final void connect(GraphicsSystem system) {
        if (system.getLight() == null) system.setLight(this);
    }

    @Override
    public final void disconnect(GraphicsSystem system) {
        if (this.equals(system.getLight())) system.setLight(null);
    }

    public final Matrix4 getViewProjectionMatrix() {
        return getProjectionMatrix().cross(getViewMatrix());
    }

    public abstract Matrix4 getViewMatrix();
    public abstract Matrix4 getProjectionMatrix();
    public abstract Vector3 getDirection();
}
