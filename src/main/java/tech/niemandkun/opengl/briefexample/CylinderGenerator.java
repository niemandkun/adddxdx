package tech.niemandkun.opengl.briefexample;

import tech.niemandkun.opengl.math.Color;
import tech.niemandkun.opengl.math.Vector3;

class CylinderGenerator {
    private final static int RESOLUTION = 100;

    private final Vector3[] mVertices;
    public Vector3[] getVertices() { return mVertices; }

    private final Color[] mColors;
    public Color[] getColors() { return mColors; }

    CylinderGenerator() {
        int trianglesCount = 4 * RESOLUTION;
        float step = (float) (2 * Math.PI / RESOLUTION);

        mVertices = new Vector3[trianglesCount * 3];
        int offset = 0;

        for (int i = 0; i < RESOLUTION; ++i) {
            float phi1 = i * step;
            float phi2 = (i + 1) * step;
            float x1 = (float) Math.cos(phi1);
            float x2 = (float) Math.cos(phi2);
            float y1 = (float) Math.sin(phi1);
            float y2 = (float) Math.sin(phi2);

            // mVertices:
            mVertices[offset]     = new Vector3(0, 0, 1);
            mVertices[offset + 1] = new Vector3(x1, y1, 1);
            mVertices[offset + 2] = new Vector3(x2, y2, 1);

            // normals:
//          new Vec4(0, 0, 1, 0),
//          new Vec4(0, 0, 1, 0),
//          new Vec4(0, 0, 1, 0),

            // mVertices:
            mVertices[offset + 3] = new Vector3(x1, y1, -1);
            mVertices[offset + 4] = new Vector3(0, 0, -1);
            mVertices[offset + 5] = new Vector3(x2, y2, -1);

            // normals:
//          new Vec4(0, 0, -1, 0),
//          new Vec4(0, 0, -1, 0),
//          new Vec4(0, 0, -1, 0),

            // mVertices:
            mVertices[offset + 6] = new Vector3(x1, y1, 1);
            mVertices[offset + 7] = new Vector3(x1, y1, -1);
            mVertices[offset + 8] = new Vector3(x2, y2, -1);

            // normals:
//          new Vec4(x1, y1, 0, 0),
//          new Vec4(x1, y1, 0, 0),
//          new Vec4(x2, y2, 0, 0),

            // mVertices:
            mVertices[offset + 9] = new Vector3(x2, y2, 1);
            mVertices[offset + 10] = new Vector3(x1, y1, 1);
            mVertices[offset + 11] = new Vector3(x2, y2, -1);

            //normals:
//          new Vec4(x2, y2, 0, 0),
//          new Vec4(x1, y1, 0, 0),
//          new Vec4(x2, y2, 0, 0),

            offset += 12;
        }

        mColors = new Color[mVertices.length];

        for (int i = 0; i < mColors.length; ++i)
            mColors[i] = Color.MATERIAL_TEAL;
    }
}
