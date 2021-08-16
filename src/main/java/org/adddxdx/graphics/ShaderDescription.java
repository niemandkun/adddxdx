/*
 * Copyright (C) 2017 Poroshin Ivan
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.graphics;

import org.jetbrains.annotations.NotNull;

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
