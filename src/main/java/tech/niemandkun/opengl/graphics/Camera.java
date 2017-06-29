package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.*;

public abstract class Camera extends GraphicsSystem.Component {
    @Override
    public final void connect(GraphicsSystem system) {
        if (system.getCamera() == null) system.setCamera(this);
    }

    @Override
    public final void disconnect(GraphicsSystem system) {
        if (this.equals(system.getCamera())) system.setCamera(null);
    }

    public final Matrix4 getViewProjectionMatrix() {
        return getProjectionMatrix().cross(getViewMatrix());
    }

    public abstract Matrix4 getViewMatrix();
    public abstract Matrix4 getProjectionMatrix();

    public abstract void adjustAspectRatio(Size targetSize);
}
