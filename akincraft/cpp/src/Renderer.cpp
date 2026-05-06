#include "Renderer.h"
#include "Chunk.h"
#include <GL/gl.h>
#include <cmath>

void Renderer::initialize() {
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
}

static void setCamera(float x, float y, float z, float yaw, float pitch) {
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glRotatef(pitch, 1.0f, 0.0f, 0.0f);
    glRotatef(yaw, 0.0f, 1.0f, 0.0f);
    glTranslatef(-x, -y, -z);
}

void Renderer::render(const World& world, float cameraX, float cameraY, float cameraZ, float yaw, float pitch) {
    setCamera(cameraX, cameraY, cameraZ, yaw, pitch);
    for (const auto& entry : world.getChunks()) {
        const Chunk& chunk = entry.second;
        for (const Quad& quad : chunk.getQuads()) {
            glBegin(GL_QUADS);
            glColor3f(quad.color[0], quad.color[1], quad.color[2]);
            for (int i = 0; i < 4; i++) {
                glVertex3f(quad.positions[i * 3], quad.positions[i * 3 + 1], quad.positions[i * 3 + 2]);
            }
            glEnd();
        }
    }
}
