package akincraft.game;

import akincraft.render.FontAtlas;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class CommandConsole {
    private static final int MAX_GLYPHS = 128;
    private static final int MAX_VERTICES = MAX_GLYPHS * 6;

    private final FontAtlas fontAtlas;
    private final int shaderProgram;
    private final int projectionLocation;
    private final int textureLocation;
    private final int vao;
    private final int vbo;
    private final StringBuilder inputBuffer = new StringBuilder();
    private final List<String> outputLines = new ArrayList<>();
    private boolean active = false;

    public CommandConsole() {
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

    public boolean isActive() {
        return active;
    }

    public void toggleActive() {
        active = !active;
        if (active) {
            inputBuffer.setLength(0);
        }
    }

    public void appendChar(char c) {
        if (!active) return;
        if (Character.isISOControl(c)) return;
        if (inputBuffer.length() < 128) {
            inputBuffer.append(c);
        }
    }

    public void backspace() {
        if (!active || inputBuffer.isEmpty()) return;
        inputBuffer.deleteCharAt(inputBuffer.length() - 1);
    }

    public String submit() {
        if (!active) return "";
        String command = inputBuffer.toString().trim();
        if (!command.isEmpty()) {
            outputLines.add(0, "> " + command);
            if (outputLines.size() > 8) {
                outputLines.remove(outputLines.size() - 1);
            }
        }
        inputBuffer.setLength(0);
        active = false;
        return command;
    }

    public void addOutput(String message) {
        outputLines.add(0, message);
        if (outputLines.size() > 8) {
            outputLines.remove(outputLines.size() - 1);
        }
    }

    public void render(int width, int height) {
        if (!active && outputLines.isEmpty()) return;

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glUseProgram(shaderProgram);
        glActiveTexture(GL_TEXTURE0);
        fontAtlas.bind();
        glUniform1i(textureLocation, 0);

        Matrix4f projection = new Matrix4f().ortho2D(0, width, height, 0);
        uploadMatrix(projectionLocation, projection);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_VERTICES * 8);
        addQuad(buffer, 20, height - 100, width - 40, 80, fontAtlas.getWhiteUv(), 0.04f, 0.07f, 0.12f, 0.94f);
        addQuad(buffer, 24, height - 96, width - 48, 24, fontAtlas.getWhiteUv(), 0.1f, 0.15f, 0.22f, 0.96f);

        float labelY = height - 92;
        addText(buffer, "COMMAND MODE", 30, labelY, 4.0f, new float[]{0.8f, 0.9f, 1.0f, 1.0f});
        addText(buffer, "/" + inputBuffer, 30, labelY + 28, 4.0f, new float[]{1.0f, 1.0f, 1.0f, 1.0f});

        float messageY = height - 130;
        for (String line : outputLines) {
            addText(buffer, line, 30, messageY, 3.0f, new float[]{0.8f, 0.8f, 0.8f, 1.0f});
            messageY -= 20;
        }

        buffer.flip();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        glDrawArrays(GL_TRIANGLES, 0, buffer.limit() / 8);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
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
