package org.adddxdx.fio;

import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

class Typeface {
    private final static String O_RDONLY = "r";

    private final int ascent;
    private final int descent;
    private final int lineGap;

    public static Typeface load(File typeface) throws IOException {
        RandomAccessFile typefaceFile = new RandomAccessFile(typeface, O_RDONLY);
        FileChannel fileChannel = typefaceFile.getChannel();
        ByteBuffer typefaceData = ByteBuffer.allocate((int) fileChannel.size());
        fileChannel.read(typefaceData);
        return new Typeface(typefaceData);
    }

    private Typeface(ByteBuffer data) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            STBTTFontinfo fontInfo = STBTTFontinfo.mallocStack(stack);
            STBTruetype.stbtt_InitFont(fontInfo, data);

            IntBuffer ascentPtr = stack.mallocInt(1);
            IntBuffer descentPtr = stack.mallocInt(1);
            IntBuffer lineGapPtr = stack.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascentPtr, descentPtr, lineGapPtr);
            ascent = ascentPtr.get();
            descent = descentPtr.get();
            lineGap = lineGapPtr.get();


        }
    }
}
