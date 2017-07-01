package tech.niemandkun.opengl.graphics.support.primitives;

import tech.niemandkun.opengl.graphics.Mesh;
import tech.niemandkun.opengl.math.Vector3;

import java.util.HashMap;
import java.util.Map;

public class PrimitivesFactory {
    private final Map<PrimitiveType, Mesh> mInstances;

    public PrimitivesFactory() {
        mInstances = new HashMap<>();
    }

    public Mesh create(PrimitiveType primitiveType) {
        if (mInstances.containsKey(primitiveType))
            return mInstances.get(primitiveType);

        Mesh mesh = createNew(primitiveType);
        mInstances.put(primitiveType, mesh);
        return mesh;
    }

    private Mesh createNew(PrimitiveType primitiveType) {
        switch (primitiveType) {
            case QUAD: return createQuad();
            case CYLINDER: return createCylinder();
        }

        throw new UnsupportedOperationException("Unsupported primitive type.");
    }

    private Mesh createQuad() {
        return new Mesh(new Vector3[] {
                new Vector3(-1.0f, -1.0f, 0.0f),
                new Vector3(1.0f, -1.0f, 0.0f),
                new Vector3(-1.0f,  1.0f, 0.0f),
                new Vector3(-1.0f,  1.0f, 0.0f),
                new Vector3(1.0f, -1.0f, 0.0f),
                new Vector3(1.0f,  1.0f, 0.0f),
        });
    }

    private Mesh createCylinder() {
        return new CylinderFactory().createCylinder();
    }
}
