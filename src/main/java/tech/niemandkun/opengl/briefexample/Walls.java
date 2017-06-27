package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.components.MeshRenderer;
import tech.niemandkun.opengl.engine.Actor;
import tech.niemandkun.opengl.graphics.Material;
import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;

public class Walls extends Actor {
    @Override
    public void onCreate() {
        super.onCreate();

        Vector3[] vertices = {

                // floor
                new Vector3(-10, 0, 10),
                new Vector3(10, 0, 10),
                new Vector3(10, 0, -10),
                new Vector3(-10, 0, 10),
                new Vector3(-10, 0, -10),
                new Vector3(10, 0, -10),

                // wall
                new Vector3(-10, 10, 10),
                new Vector3(10, 10, 10),
                new Vector3(10, 0, 10),
                new Vector3(-10, 10, 10),
                new Vector3(-10, 0, 10),
                new Vector3(10, 0, 10),
        };

        Color floorColor = new Color(0x99DCAAFF);
        Color wallColor = new Color(0xDCDCAAFF);

        Color[] colors = {
                floorColor, floorColor, floorColor,
                floorColor, floorColor, floorColor,

                wallColor, wallColor, wallColor,
                wallColor, wallColor, wallColor,
        };

        Mesh mesh = new Mesh(vertices, colors);
        Material material = getScene().getMaterialFactory().getDefaultMaterial();
        MeshRenderer renderer = new MeshRenderer(mesh, material);

        addComponent(renderer);
    }
}
