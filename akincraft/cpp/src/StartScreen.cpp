#include "StartScreen.h"
#include <GL/gl.h>
#include <cmath>
#include <cstring>

StartScreen::StartScreen()
    : textureId(0) {
}

StartScreen::~StartScreen() {
    if (textureId != 0) {
        glDeleteTextures(1, &textureId);
    }
}

void StartScreen::initialize() {
    buildTexture();
}

void StartScreen::render(int width, int height) {
    glDisable(GL_DEPTH_TEST);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, textureId);

    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0, width, height, 0, -1, 1);

    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();

    drawRect(0, 0, width, height, 0.08f, 0.12f, 0.18f, 1.0f);
    drawRect(width * 0.18f, height * 0.24f, width * 0.64f, height * 0.52f, 0.08f, 0.12f, 0.20f, 0.95f);
    drawText("AKINCRAFT", width * 0.25f, height * 0.33f, 6.0f);
    drawText("PRESS ENTER", width * 0.35f, height * 0.57f, 4.0f);

    glPopMatrix();
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);

    glBindTexture(GL_TEXTURE_2D, 0);
    glDisable(GL_BLEND);
    glDisable(GL_TEXTURE_2D);
    glEnable(GL_DEPTH_TEST);
}

bool StartScreen::shouldStart(GLFWwindow* window) const {
    return glfwGetKey(window, GLFW_KEY_ENTER) == GLFW_PRESS;
}

void StartScreen::buildTexture() {
    unsigned char pixels[TEXTURE_SIZE * TEXTURE_SIZE * 4];
    std::memset(pixels, 0, sizeof(pixels));

    fillCell(pixels, 0, false);
    fillCell(pixels, 1, true);

    const int A[7] = {0b01110, 0b10001, 0b10001, 0b11111, 0b10001, 0b10001, 0b10001};
    const int C[7] = {0b01110, 0b10001, 0b10000, 0b10000, 0b10000, 0b10001, 0b01110};
    const int E[7] = {0b11111, 0b10000, 0b10000, 0b11110, 0b10000, 0b10000, 0b11111};
    const int F[7] = {0b11111, 0b10000, 0b10000, 0b11110, 0b10000, 0b10000, 0b10000};
    const int I[7] = {0b11111, 0b00100, 0b00100, 0b00100, 0b00100, 0b00100, 0b11111};
    const int K[7] = {0b10001, 0b10010, 0b10100, 0b11000, 0b10100, 0b10010, 0b10001};
    const int N[7] = {0b10001, 0b11001, 0b10101, 0b10011, 0b10001, 0b10001, 0b10001};
    const int P[7] = {0b11110, 0b10001, 0b10001, 0b11110, 0b10000, 0b10000, 0b10000};
    const int R[7] = {0b11110, 0b10001, 0b10001, 0b11110, 0b10100, 0b10010, 0b10001};
    const int S[7] = {0b01111, 0b10000, 0b10000, 0b01110, 0b00001, 0b00001, 0b11110};
    const int T[7] = {0b11111, 0b00100, 0b00100, 0b00100, 0b00100, 0b00100, 0b00100};

    setGlyph(pixels, 2, A);
    setGlyph(pixels, 3, C);
    setGlyph(pixels, 4, E);
    setGlyph(pixels, 5, F);
    setGlyph(pixels, 6, I);
    setGlyph(pixels, 7, K);
    setGlyph(pixels, 8, N);
    setGlyph(pixels, 9, P);
    setGlyph(pixels, 10, R);
    setGlyph(pixels, 11, S);
    setGlyph(pixels, 12, T);

    glGenTextures(1, &textureId);
    glBindTexture(GL_TEXTURE_2D, textureId);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, TEXTURE_SIZE, TEXTURE_SIZE, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
    glBindTexture(GL_TEXTURE_2D, 0);
}

void StartScreen::fillCell(unsigned char* pixels, int index, bool white) {
    int cellX = index % GRID_COLS;
    int baseX = cellX * CELL_SIZE;
    for (int row = 0; row < CELL_SIZE; row++) {
        for (int col = 0; col < CELL_SIZE; col++) {
            int offset = ((row * TEXTURE_SIZE) + baseX + col) * 4;
            if (white) {
                pixels[offset] = 255;
                pixels[offset + 1] = 255;
                pixels[offset + 2] = 255;
                pixels[offset + 3] = 255;
            } else {
                pixels[offset] = 0;
                pixels[offset + 1] = 0;
                pixels[offset + 2] = 0;
                pixels[offset + 3] = 0;
            }
        }
    }
}

void StartScreen::setGlyph(unsigned char* pixels, int index, const int pattern[7]) {
    int cellX = index % GRID_COLS;
    int baseX = cellX * CELL_SIZE;
    for (int row = 0; row < 7; row++) {
        for (int col = 0; col < 5; col++) {
            if ((pattern[row] & (1 << (4 - col))) != 0) {
                int px = baseX + 1 + col;
                int py = 1 + row;
                int offset = ((py * TEXTURE_SIZE) + px) * 4;
                pixels[offset] = 255;
                pixels[offset + 1] = 255;
                pixels[offset + 2] = 255;
                pixels[offset + 3] = 255;
            }
        }
    }
}

int StartScreen::getGlyphIndex(char character) const {
    switch (character) {
        case 'A': return 2;
        case 'C': return 3;
        case 'E': return 4;
        case 'F': return 5;
        case 'I': return 6;
        case 'K': return 7;
        case 'N': return 8;
        case 'P': return 9;
        case 'R': return 10;
        case 'S': return 11;
        case 'T': return 12;
        case ' ': return 0;
        default: return 0;
    }
}

void StartScreen::drawText(const std::string& text, float x, float y, float scale) {
    float advance = 6.0f * scale;
    for (char raw : text) {
        char c = raw;
        int index = getGlyphIndex(c);
        float texU0 = (index * CELL_SIZE) / (float)TEXTURE_SIZE;
        float texU1 = texU0 + CELL_SIZE / (float)TEXTURE_SIZE;
        float texV0 = 0.0f;
        float texV1 = 1.0f;
        float width = 5.0f * scale;
        float height = 7.0f * scale;

        glColor4f(0.87f, 0.80f, 0.54f, 1.0f);
        glBegin(GL_QUADS);
        glTexCoord2f(texU0, texV0); glVertex2f(x, y);
        glTexCoord2f(texU1, texV0); glVertex2f(x + width, y);
        glTexCoord2f(texU1, texV1); glVertex2f(x + width, y + height);
        glTexCoord2f(texU0, texV1); glVertex2f(x, y + height);
        glEnd();

        x += advance;
    }
}

void StartScreen::drawRect(float x, float y, float width, float height, float r, float g, float b, float a) {
    glDisable(GL_TEXTURE_2D);
    glColor4f(r, g, b, a);
    glBegin(GL_QUADS);
    glVertex2f(x, y);
    glVertex2f(x + width, y);
    glVertex2f(x + width, y + height);
    glVertex2f(x, y + height);
    glEnd();
    glEnable(GL_TEXTURE_2D);
}
