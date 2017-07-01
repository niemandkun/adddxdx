package tech.niemandkun.opengl.fio;

import org.lwjgl.system.MemoryStack;
import tech.niemandkun.opengl.engine.Destroyable;
import tech.niemandkun.opengl.math.Size;

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
