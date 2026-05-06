package akincraft.render;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

public class FontAtlas {
    private static final int CELL_SIZE = 8;
    private static final int GRID_COLUMNS = 16;
    private static final int TEXTURE_SIZE = CELL_SIZE * GRID_COLUMNS;

    private final int textureId;
    private final Map<Character, Integer> glyphIndex = new HashMap<>();

    public FontAtlas() {
        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        buildGlyphMap();
        ByteBuffer pixels = BufferUtils.createByteBuffer(TEXTURE_SIZE * TEXTURE_SIZE * 4);
        fillPixels(pixels);
        pixels.flip();

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, TEXTURE_SIZE, TEXTURE_SIZE, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public float[] getUvForChar(char character) {
        int index = glyphIndex.getOrDefault(Character.toUpperCase(character), 0);
        return getUvFromIndex(index);
    }

    public float[] getWhiteUv() {
        return getUvFromIndex(1);
    }

    private void buildGlyphMap() {
        glyphIndex.put(' ', 0);
        glyphIndex.put('A', 2);
        glyphIndex.put('B', 3);
        glyphIndex.put('C', 4);
        glyphIndex.put('D', 5);
        glyphIndex.put('E', 6);
        glyphIndex.put('F', 7);
        glyphIndex.put('G', 8);
        glyphIndex.put('H', 9);
        glyphIndex.put('I', 10);
        glyphIndex.put('J', 11);
        glyphIndex.put('K', 12);
        glyphIndex.put('L', 13);
        glyphIndex.put('M', 14);
        glyphIndex.put('N', 15);
        glyphIndex.put('O', 16);
        glyphIndex.put('P', 17);
        glyphIndex.put('Q', 18);
        glyphIndex.put('R', 19);
        glyphIndex.put('S', 20);
        glyphIndex.put('T', 21);
        glyphIndex.put('U', 22);
        glyphIndex.put('V', 23);
        glyphIndex.put('W', 24);
        glyphIndex.put('X', 25);
        glyphIndex.put('Y', 26);
        glyphIndex.put('Z', 27);
        glyphIndex.put('/', 28);
        glyphIndex.put('0', 29);
        glyphIndex.put('1', 30);
        glyphIndex.put('2', 31);
        glyphIndex.put('3', 32);
        glyphIndex.put('4', 33);
        glyphIndex.put('5', 34);
        glyphIndex.put('6', 35);
        glyphIndex.put('7', 36);
        glyphIndex.put('8', 37);
        glyphIndex.put('9', 38);
        glyphIndex.put('-', 39);
    }

    private void fillPixels(ByteBuffer pixels) {
        for (int i = 0; i < TEXTURE_SIZE * TEXTURE_SIZE; i++) {
            pixels.put((byte) 0);
            pixels.put((byte) 0);
            pixels.put((byte) 0);
            pixels.put((byte) 0);
        }

        fillCell(pixels, 0, false);
        fillCell(pixels, 1, true);

        setGlyph(2, new int[]{0b01110, 0b10001, 0b10001, 0b11111, 0b10001, 0b10001, 0b10001}, pixels);
        setGlyph(3, new int[]{0b11110, 0b10001, 0b10001, 0b11110, 0b10001, 0b10001, 0b11110}, pixels);
        setGlyph(4, new int[]{0b01110, 0b10001, 0b10000, 0b10000, 0b10000, 0b10001, 0b01110}, pixels);
        setGlyph(5, new int[]{0b11110, 0b10001, 0b10001, 0b10001, 0b10001, 0b10001, 0b11110}, pixels);
        setGlyph(6, new int[]{0b11111, 0b10000, 0b10000, 0b11110, 0b10000, 0b10000, 0b11111}, pixels);
        setGlyph(7, new int[]{0b11111, 0b10000, 0b10000, 0b11110, 0b10000, 0b10000, 0b10000}, pixels);
        setGlyph(8, new int[]{0b01110, 0b10001, 0b10000, 0b10111, 0b10001, 0b10001, 0b01110}, pixels);
        setGlyph(9, new int[]{0b10001, 0b10001, 0b10001, 0b11111, 0b10001, 0b10001, 0b10001}, pixels);
        setGlyph(10, new int[]{0b11111, 0b00100, 0b00100, 0b00100, 0b00100, 0b00100, 0b11111}, pixels);
        setGlyph(11, new int[]{0b00111, 0b00010, 0b00010, 0b00010, 0b10010, 0b10010, 0b01100}, pixels);
        setGlyph(12, new int[]{0b10001, 0b10010, 0b10100, 0b11000, 0b10100, 0b10010, 0b10001}, pixels);
        setGlyph(13, new int[]{0b10000, 0b10000, 0b10000, 0b10000, 0b10000, 0b10000, 0b11111}, pixels);
        setGlyph(14, new int[]{0b10001, 0b11011, 0b10101, 0b10101, 0b10001, 0b10001, 0b10001}, pixels);
        setGlyph(15, new int[]{0b10001, 0b11001, 0b10101, 0b10011, 0b10001, 0b10001, 0b10001}, pixels);
        setGlyph(16, new int[]{0b01110, 0b10001, 0b10001, 0b10001, 0b10001, 0b10001, 0b01110}, pixels);
        setGlyph(17, new int[]{0b11110, 0b10001, 0b10001, 0b11110, 0b10000, 0b10000, 0b10000}, pixels);
        setGlyph(18, new int[]{0b01110, 0b10001, 0b10001, 0b10001, 0b10101, 0b10010, 0b01101}, pixels);
        setGlyph(19, new int[]{0b11110, 0b10001, 0b10001, 0b11110, 0b10100, 0b10010, 0b10001}, pixels);
        setGlyph(20, new int[]{0b01111, 0b10000, 0b10000, 0b01110, 0b00001, 0b00001, 0b11110}, pixels);
        setGlyph(21, new int[]{0b11111, 0b00100, 0b00100, 0b00100, 0b00100, 0b00100, 0b00100}, pixels);
        setGlyph(22, new int[]{0b10001, 0b10001, 0b10001, 0b10001, 0b10001, 0b10001, 0b01110}, pixels);
        setGlyph(23, new int[]{0b10001, 0b10001, 0b10001, 0b10001, 0b01010, 0b01010, 0b00100}, pixels);
        setGlyph(24, new int[]{0b10001, 0b10001, 0b10001, 0b10101, 0b10101, 0b11011, 0b10001}, pixels);
        setGlyph(25, new int[]{0b10001, 0b10001, 0b01010, 0b00100, 0b01010, 0b10001, 0b10001}, pixels);
        setGlyph(26, new int[]{0b10001, 0b10001, 0b01010, 0b00100, 0b00100, 0b00100, 0b00100}, pixels);
        setGlyph(27, new int[]{0b11111, 0b00001, 0b00010, 0b00100, 0b01000, 0b10000, 0b11111}, pixels);
        setGlyph(28, new int[]{0b00001, 0b00010, 0b00100, 0b01000, 0b10000, 0b00000, 0b00000}, pixels);
        setGlyph(29, new int[]{0b01110, 0b10001, 0b10011, 0b10101, 0b11001, 0b10001, 0b01110}, pixels);
        setGlyph(30, new int[]{0b00100, 0b01100, 0b00100, 0b00100, 0b00100, 0b00100, 0b01110}, pixels);
        setGlyph(31, new int[]{0b01110, 0b10001, 0b00001, 0b00010, 0b00100, 0b01000, 0b11111}, pixels);
        setGlyph(32, new int[]{0b01110, 0b10001, 0b00001, 0b00110, 0b00001, 0b10001, 0b01110}, pixels);
        setGlyph(33, new int[]{0b00010, 0b00110, 0b01010, 0b10010, 0b11111, 0b00010, 0b00010}, pixels);
        setGlyph(34, new int[]{0b00100, 0b01100, 0b01010, 0b10010, 0b11111, 0b00010, 0b00010}, pixels);
        setGlyph(35, new int[]{0b11111, 0b10000, 0b11110, 0b00001, 0b00001, 0b10001, 0b01110}, pixels);
        setGlyph(36, new int[]{0b01110, 0b10000, 0b11110, 0b10001, 0b10001, 0b10001, 0b01110}, pixels);
        setGlyph(37, new int[]{0b11111, 0b00001, 0b00010, 0b00100, 0b01000, 0b01000, 0b01000}, pixels);
        setGlyph(38, new int[]{0b01110, 0b10001, 0b10001, 0b01110, 0b10001, 0b10001, 0b01110}, pixels);
        setGlyph(39, new int[]{0b01110, 0b10001, 0b10001, 0b01111, 0b00001, 0b00001, 0b01110}, pixels);
        setGlyph(40, new int[]{0b00000, 0b00000, 0b00000, 0b11111, 0b00000, 0b00000, 0b00000}, pixels);
    }

    private void fillCell(ByteBuffer pixels, int index, boolean white) {
        int cellX = index % GRID_COLUMNS;
        int baseX = cellX * CELL_SIZE;
        int baseY = 0;
        for (int row = 0; row < CELL_SIZE; row++) {
            for (int col = 0; col < CELL_SIZE; col++) {
                int offset = ((baseY + row) * TEXTURE_SIZE + baseX + col) * 4;
                if (white) {
                    pixels.put(offset, (byte) 255);
                    pixels.put(offset + 1, (byte) 255);
                    pixels.put(offset + 2, (byte) 255);
                    pixels.put(offset + 3, (byte) 255);
                } else {
                    pixels.put(offset, (byte) 0);
                    pixels.put(offset + 1, (byte) 0);
                    pixels.put(offset + 2, (byte) 0);
                    pixels.put(offset + 3, (byte) 0);
                }
            }
        }
    }

    private void setGlyph(int index, int[] pattern, ByteBuffer pixels) {
        int cellX = index % GRID_COLUMNS;
        int baseX = cellX * CELL_SIZE;
        int baseY = 0;
        for (int row = 0; row < pattern.length; row++) {
            for (int col = 0; col < 5; col++) {
                if ((pattern[row] & (1 << (4 - col))) != 0) {
                    int px = baseX + 1 + col;
                    int py = baseY + 1 + row;
                    int offset = (py * TEXTURE_SIZE + px) * 4;
                    pixels.put(offset, (byte) 255);
                    pixels.put(offset + 1, (byte) 255);
                    pixels.put(offset + 2, (byte) 255);
                    pixels.put(offset + 3, (byte) 255);
                }
            }
        }
    }

    private float[] getUvFromIndex(int index) {
        float step = 1.0f / GRID_COLUMNS;
        float u0 = index * step;
        float v0 = 0.0f;
        float u1 = u0 + step;
        float v1 = 1.0f;
        return new float[]{u0, v0, u1, v1};
    }
}
