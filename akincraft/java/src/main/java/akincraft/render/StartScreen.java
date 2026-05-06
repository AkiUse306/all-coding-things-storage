package akincraft.render;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL13.*;

public class StartScreen {
    private static final int MAX_GLYPHS = 64;
    private static final int MAX_VERTICES = MAX_GLYPHS * 6;

    private final FontAtlas fontAtlas;
    private final int shaderProgram;
    private final int projectionLocation;
    private final int textureLocation;
    private final int vao;
    private final int vbo;

    public StartScreen() {
        fontAtlas = new FontAtlas();
        shaderProgram = createProgram();
        projectionLocation = glGetUniformLocation(shaderProgram, "projection");
        textureLocation = glGetUniformLocation(shaderProgram, "fontTexture");

        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, MAX_VERTICES * 8 * Float.BYTES, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 8 * Float.BYTES, 4 * Float.BYTES);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void render(int width, int height) {
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glUseProgram(shaderProgram);
        glActiveTexture(GL_TEXTURE0);
        fontAtlas.bind();
        glUniform1i(textureLocation, 0);

        Matrix4f projection = new Matrix4f().ortho2D(0, width, height, 0);
        uploadMatrix(projectionLocation, projection);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 8);
        buildBackground(vertexBuffer, width, height);
        buildTitle(vertexBuffer, width, height);
        buildPrompt(vertexBuffer, width, height);
        vertexBuffer.flip();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);
        glDrawArrays(GL_TRIANGLES, 0, vertexBuffer.limit() / 8);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
    }

    private void buildBackground(FloatBuffer buffer, int width, int height) {
        float[] whiteUv = fontAtlas.getWhiteUv();
        addQuad(buffer, 0, 0, width, height, whiteUv, 0.05f, 0.08f, 0.18f, 1.0f);
        addQuad(buffer, width * 0.18f, height * 0.24f, width * 0.64f, height * 0.52f, whiteUv, 0.08f, 0.12f, 0.20f, 0.95f);
    }

    private void buildTitle(FloatBuffer buffer, int width, int height) {
        String title = "AKINCRAFT";
        float scale = 12.0f;
        float textWidth = title.length() * (6.0f * scale);
        float x = (width - textWidth) * 0.5f;
        float y = height * 0.33f;
        addText(buffer, title, x, y, scale, new float[]{0.87f, 0.80f, 0.54f, 1.0f});
    }

    private void buildPrompt(FloatBuffer buffer, int width, int height) {
        String prompt = "PRESS ENTER";
        float scale = 6.0f;
        float textWidth = prompt.length() * (6.0f * scale);
        float x = (width - textWidth) * 0.5f;
        float y = height * 0.57f;
        addText(buffer, prompt, x, y, scale, new float[]{1.0f, 1.0f, 1.0f, 0.95f});
    }

    private void addText(FloatBuffer buffer, String text, float x, float y, float scale, float[] color) {
        for (int i = 0; i < text.length(); i++) {
            float[] uv = fontAtlas.getUvForChar(text.charAt(i));
            addQuad(buffer, x, y, 5.0f * scale, 7.0f * scale, uv, color[0], color[1], color[2], color[3]);
            x += 6.0f * scale;
        }
    }

    private void addQuad(FloatBuffer buffer, float x, float y, float width, float height, float[] uv, float r, float g, float b, float a) {
        float u0 = uv[0];
        float v0 = uv[1];
        float u1 = uv[2];
        float v1 = uv[3];

        buffer.put(x).put(y).put(u0).put(v0).put(r).put(g).put(b).put(a);
        buffer.put(x + width).put(y).put(u1).put(v0).put(r).put(g).put(b).put(a);
        buffer.put(x + width).put(y + height).put(u1).put(v1).put(r).put(g).put(b).put(a);

        buffer.put(x).put(y).put(u0).put(v0).put(r).put(g).put(b).put(a);
        buffer.put(x + width).put(y + height).put(u1).put(v1).put(r).put(g).put(b).put(a);
        buffer.put(x).put(y + height).put(u0).put(v1).put(r).put(g).put(b).put(a);
    }

    private int createProgram() {
        int vertex = createShader(GL_VERTEX_SHADER,
                "#version 330 core\n" +
                        "layout(location = 0) in vec2 position;\n" +
                        "layout(location = 1) in vec2 uv;\n" +
                        "layout(location = 2) in vec4 color;\n" +
                        "uniform mat4 projection;\n" +
                        "out vec2 fragUv;\n" +
                        "out vec4 fragColor;\n" +
                        "void main() {\n" +
                        "    fragUv = uv;\n" +
                        "    fragColor = color;\n" +
                        "    gl_Position = projection * vec4(position, 0.0, 1.0);\n" +
                        "}");

        int fragment = createShader(GL_FRAGMENT_SHADER,
                "#version 330 core\n" +
                        "in vec2 fragUv;\n" +
                        "in vec4 fragColor;\n" +
                        "uniform sampler2D fontTexture;\n" +
                        "out vec4 outColor;\n" +
                        "void main() {\n" +
                        "    vec4 sample = texture(fontTexture, fragUv);\n" +
                        "    outColor = vec4(fragColor.rgb, sample.a * fragColor.a);\n" +
                        "}");

        int program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException(glGetProgramInfoLog(program));
        }
        glDeleteShader(vertex);
        glDeleteShader(fragment);
        return program;
    }

    private int createShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException(glGetShaderInfoLog(shader));
        }
        return shader;
    }

    private void uploadMatrix(int location, Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }
}
