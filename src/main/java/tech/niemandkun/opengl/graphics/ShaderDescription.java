package tech.niemandkun.opengl.graphics;

public class ShaderDescription {
    private final String mVertexShaderName;
    private final String mFragmentShaderName;

    private ShaderDescription(String vertexShaderName, String fragmentShaderName) {
        mVertexShaderName = vertexShaderName;
        mFragmentShaderName = fragmentShaderName;
    }

    public static ShaderDescription forFile(String allShaderPartsCommonName) {
        return new ShaderDescription(allShaderPartsCommonName, allShaderPartsCommonName);
    }

    public static ShaderDescription forFiles(String vertexShaderName, String fragmentShaderName) {
        return new ShaderDescription(vertexShaderName, fragmentShaderName);
    }

    String getVertexShaderName() {
        return mVertexShaderName;
    }

    String getFragmentShaderName() {
        return mFragmentShaderName;
    }
}
