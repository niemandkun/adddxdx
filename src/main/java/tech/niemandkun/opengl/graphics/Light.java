package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Matrix4;
import tech.niemandkun.opengl.math.Projection;

public class Light extends GraphicsSystem.Component {
    @Override
    public void connect(GraphicsSystem system) {
        if (system.getLight() == null) system.setLight(this);
    }

    @Override
    public void disconnect(GraphicsSystem system) {
        if (this.equals(system.getLight())) system.setLight(null);
    }

    Matrix4 getMatrix() {
        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
        Matrix4 cameraTransform = Matrix4.getRotationMatrix(0, (float) Math.PI, 0);
        Matrix4 projectionMatrix = Projection.ortho(-15, 10, -20, 20, -50, 50);
        return projectionMatrix.cross(viewMatrix.cross(cameraTransform).inverse());
    }
}
