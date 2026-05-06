#pragma once

#include <GLFW/glfw3.h>
#include <string>

class StartScreen {
public:
    StartScreen();
    ~StartScreen();
    void initialize();
    void render(int width, int height);
    bool shouldStart(GLFWwindow* window) const;

private:
    unsigned int textureId;
    static const int CELL_SIZE = 8;
    static const int GRID_COLS = 16;
    static const int TEXTURE_SIZE = CELL_SIZE * GRID_COLS;

    void buildTexture();
    void fillCell(unsigned char* pixels, int index, bool white);
    void setGlyph(unsigned char* pixels, int index, const int pattern[7]);
    int getGlyphIndex(char character) const;
    void drawText(const std::string& text, float x, float y, float scale);
    void drawRect(float x, float y, float width, float height, float r, float g, float b, float a);
};
