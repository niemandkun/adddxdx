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

package org.adddxdx.fio;

import org.adddxdx.engine.Destroyable;
import org.adddxdx.math.Size;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;

public class Image implements Destroyable {
    private final ByteBuffer mImageBytes;
    private final Size mSize;
    private final int mChannelsCount;

    public static Image load(File file) {
        return new Image(file);
    }

    private Image(File file) {
        try (MemoryStack stack = MemoryStack.stackPush()) {

            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channelsCount = stack.mallocInt(1);

            mImageBytes = stbi_load(file.getAbsolutePath(), width, height, channelsCount, STBI_rgb_alpha);

            mSize = new Size(width.get(0), height.get(0));
            mChannelsCount = channelsCount.get(0);
        }
    }

    public ByteBuffer getImageBytes() {
        return mImageBytes;
    }

    public Size getSize() {
        return mSize;
    }

    public int getChannelsCount() {
        return mChannelsCount;
    }

    @Override
    public void destroy() {
        stbi_image_free(mImageBytes);
    }
}
