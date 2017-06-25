package tech.niemandkun.opengl.math;

import java.nio.ByteBuffer;

@SuppressWarnings("NumericOverflow")
class ColorBitsConverter {
    private final static int RED_SHIFT = 24;
    private final static int GREEN_SHIFT = 16;
    private final static int BLUE_SHIFT = 8;
    private final static int ALPHA_SHIFT = 0;

    private final static int BYTE_MASK = 0xff;
    private final static float BYTE_FLOAT = BYTE_MASK;

    static int rgbaToInt(byte red, byte green, byte blue, byte alpha) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {red, green, blue, alpha});
        return buffer.getInt();
    }

    static byte[] intToRgba(int color) {
        byte red = (byte) (color >> RED_SHIFT);
        byte green = (byte) (color >> GREEN_SHIFT);
        byte blue = (byte) (color >> BLUE_SHIFT);
        byte alpha = (byte) (color >> ALPHA_SHIFT);
        return new byte[] {red, green, blue, alpha};
    }

    static byte[] rgbFloatToByte(float[] rgb) {
        return new byte[] {
                (byte)(BYTE_MASK * rgb[0]),
                (byte)(BYTE_MASK * rgb[1]),
                (byte)(BYTE_MASK * rgb[2]),
        };
    }

    static byte[] rgbaFloatToByte(float[] rgba) {
        return new byte[] {
                (byte)(BYTE_MASK * rgba[0]),
                (byte)(BYTE_MASK * rgba[1]),
                (byte)(BYTE_MASK * rgba[2]),
                (byte)(BYTE_MASK * rgba[3]),
        };
    }

    static float[] rgbByteToFloat(byte[] rgb) {
        return new float[] {
                (rgb[0] & BYTE_MASK) / BYTE_FLOAT,
                (rgb[1] & BYTE_MASK) / BYTE_FLOAT,
                (rgb[2] & BYTE_MASK) / BYTE_FLOAT,
        };
    }

    static float[] rgbaByteToFloat(byte[] rgba) {
        return new float[] {
                (rgba[0] & BYTE_MASK) / BYTE_FLOAT,
                (rgba[1] & BYTE_MASK) / BYTE_FLOAT,
                (rgba[2] & BYTE_MASK) / BYTE_FLOAT,
                (rgba[3] & BYTE_MASK) / BYTE_FLOAT,
        };
    }
}
