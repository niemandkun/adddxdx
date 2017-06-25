package tech.niemandkun.opengl.math;

import java.io.Serializable;

public class Color implements Serializable {
    public static final Color WHITE = new Color(0xffffffff);
    public static final Color BLACK = new Color(0x000000ff);

    public static final Color MATERIAL_PURPLE = new Color(0x9c26b0ff);
    public static final Color MATERIAL_LIGHT_BLUE = new Color(0x02a8f4ff);
    public static final Color MATERIAL_TEAL = new Color(0x009687ff);
    public static final Color MATERIAL_GREEN = new Color(0x4caf4fff);
    public static final Color MATERIAL_YELLOW = new Color(0xffef58ff);
    public static final Color MATERIAL_DEEP_ORANGE = new Color(0xff5622ff);

    private static final ColorStringFormatter FORMATTER = new ColorStringFormatter();

    private final byte mRed;
    private final byte mGreen;
    private final byte mBlue;
    private final byte mAlpha;

    public byte getRed() { return mRed; }
    public byte getGreen() { return mGreen; }
    public byte getBlue() { return mBlue; }
    public byte getAlpha() { return mAlpha; }

    public Color(int red, int green, int blue, int alpha) {
        mRed = (byte) red;
        mGreen = (byte) green;
        mBlue = (byte) blue;
        mAlpha = (byte) alpha;
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 0xff);
    }

    public Color(int color) {
        this(ColorBitsConverter.intToRgba(color));
    }

    private Color(byte[] rgbaArray) {
        this(rgbaArray[0], rgbaArray[1], rgbaArray[2], rgbaArray[3]);
    }

    public Color(Vector3 color) {
        this(color.toVec4());
    }

    public Color(Vector4 color) {
        this(ColorBitsConverter.rgbaFloatToByte(color.toFloatArray()));
    }

    public int toInteger() {
        return ColorBitsConverter.rgbaToInt(mRed, mGreen, mBlue, mAlpha);
    }

    public Vector3 toVector3() {
        return Vector3.fromFloatArray(ColorBitsConverter.rgbByteToFloat(
                new byte[] {mRed, mGreen, mBlue}
        ));
    }

    public Vector4 toVector4() {
        return Vector4.fromFloatArray(ColorBitsConverter.rgbaByteToFloat(
                new byte[] {mRed, mGreen, mBlue, mAlpha}
        ));
    }

    @Override
    public String toString() {
        return "#" + String.format("%08x", toInteger());
    }

    public int serialize() {
        return toInteger();
    }

    public static Color deserialize(int serialized) {
        return new Color(serialized);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Color color = (Color) other;
        return this.mRed == color.mRed
                && this.mGreen == color.mGreen
                && this.mBlue == color.mBlue
                && this.mAlpha == color.mAlpha;
    }

    @Override
    public int hashCode() {
        int result = (int) mRed;
        result = 31 * result + (int) mGreen;
        result = 31 * result + (int) mBlue;
        result = 31 * result + (int) mAlpha;
        return result;
    }

    public static Color parse(String color) {
        return FORMATTER.parse(color);
    }

    private static class ColorStringFormatter {
        private final static int RED_OFFSET = 0;
        private final static int GREEN_OFFSET = 2;
        private final static int BLUE_OFFSET = 4;
        private final static int ALPHA_OFFSET = 6;

        private final static int RGB_STRING_LENGTH = ALPHA_OFFSET;
        private final static int RGBA_STRING_LENGTH = RGB_STRING_LENGTH + 2;

        private final static int BASE_10 = 0x10;

        Color parse(String color) {
            if (color.startsWith("#"))
                color = color.substring(1);

            if (color.length() != RGB_STRING_LENGTH && color.length() != RGBA_STRING_LENGTH)
                throw new IllegalArgumentException(
                    "Expected string to represent integer, or one of the following formats: #rrggbb or #aarrggbb.");

            int r = Integer.parseInt(color.substring(RED_OFFSET, GREEN_OFFSET), BASE_10);
            int g = Integer.parseInt(color.substring(GREEN_OFFSET, BLUE_OFFSET), BASE_10);
            int b = Integer.parseInt(color.substring(BLUE_OFFSET, ALPHA_OFFSET), BASE_10);

            int a = color.length() == RGBA_STRING_LENGTH
                    ? Integer.parseInt(color.substring(ALPHA_OFFSET, RGBA_STRING_LENGTH), BASE_10)
                    : (byte) 0xff;

            return new Color(r, g, b, a);
        }
    }
}
