package tech.niemandkun.opengl.graphics;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

public class ShaderDescription {
    private final @NotNull String mVertexShaderName;
    private final @NotNull String mFragmentShaderName;

    private ShaderDescription(@NotNull String vertexShaderName, @NotNull String fragmentShaderName) {
        Objects.requireNonNull(vertexShaderName);
        Objects.requireNonNull(fragmentShaderName);

        mVertexShaderName = vertexShaderName;
        mFragmentShaderName = fragmentShaderName;
    }

    public static ShaderDescription forFile(@NotNull String allShaderPartsCommonName) {
        return new ShaderDescription(allShaderPartsCommonName, allShaderPartsCommonName);
    }

    public static ShaderDescription forFiles(@NotNull String vertexShaderName, @NotNull String fragmentShaderName) {
        return new ShaderDescription(vertexShaderName, fragmentShaderName);
    }

    String getVertexShaderName() {
        return mVertexShaderName;
    }

    String getFragmentShaderName() {
        return mFragmentShaderName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        ShaderDescription that = (ShaderDescription) other;

        return  mVertexShaderName.equals(that.mVertexShaderName)
                && mFragmentShaderName.equals(that.mFragmentShaderName);
    }

    @Override
    public int hashCode() {
        return mVertexShaderName.hashCode() + 17 * mFragmentShaderName.hashCode();
    }
}
