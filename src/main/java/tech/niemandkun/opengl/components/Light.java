package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.graphics.GraphicsSystem;
import tech.niemandkun.opengl.math.Matrix4;
import tech.niemandkun.opengl.math.Projection;

public class Light extends GraphicsSystem.Component {
    @Override
    public void connect(GraphicsSystem system) {

    }

    @Override
    public void disconnect(GraphicsSystem system) {

    }

    public Matrix4 getMatrix() {
//        Matrix4 viewMatrix = getActor().getTransform().getMatrix();
//        Matrix4 cameraTransform = Matrix4.getRotationMatrix(0, (float) Math.PI, 0);
//        Matrix4 projectionMatrix = Projection.perspective(mFieldOfView, mAspectRatio, mNearPlane, mFarPlane);
//        return projectionMatrix.cross(viewMatrix.cross(cameraTransform).inverse());
        return null;
    }
}
