package akincraft.render;

import akincraft.game.PlayerController;
import akincraft.world.Chunk;
import akincraft.world.WorldManager;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private final WorldManager world;
    private final PlayerController player;
    private final TextureAtlas atlas;
    private final int shaderProgram;
    private final int projectionLocation;
    private final int viewLocation;
    private final int textureLocation;
    private float elapsedTime;

    public Renderer(WorldManager world, PlayerController player) {
        this.world = world;
        this.player = player;
        atlas = new TextureAtlas();
        shaderProgram = createProgram();
        projectionLocation = glGetUniformLocation(shaderProgram, "projection");
        viewLocation = glGetUniformLocation(shaderProgram, "view");
        textureLocation = glGetUniformLocation(shaderProgram, "atlasTexture");
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    public void render(float delta) {
        elapsedTime += delta;
        Vector3f sky = calculateSkyColor(elapsedTime);
        glClearColor(sky.x, sky.y, sky.z, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUseProgram(shaderProgram);
        glActiveTexture(GL_TEXTURE0);
        atlas.bind();
        glUniform1i(textureLocation, 0);

        Matrix4f projection = new Matrix4f().perspective((float) Math.toRadians(70.0), 1280f / 720f, 0.1f, 1000f);
        Matrix4f view = calculateViewMatrix();
        uploadMatrix(projectionLocation, projection);
        uploadMatrix(viewLocation, view);

        for (Chunk chunk : world.getChunks().values()) {
            drawMesh(chunk);
        }
    }

    private Matrix4f calculateViewMatrix() {
        Vector3f eye = player.getPosition();
        Vector3f forward = new Vector3f();
        forward.x = (float) Math.cos(Math.toRadians(player.getYaw())) * (float) Math.cos(Math.toRadians(player.getPitch()));
        forward.y = (float) Math.sin(Math.toRadians(player.getPitch()));
        forward.z = (float) Math.sin(Math.toRadians(player.getYaw())) * (float) Math.cos(Math.toRadians(player.getPitch()));
        Vector3f center = new Vector3f(eye).add(forward);
        return new Matrix4f().lookAt(eye, center, new Vector3f(0, 1, 0));
    }

    private Vector3f calculateSkyColor(float time) {
        float cycle = (float) ((Math.sin(time * 0.12f) + 1.0) * 0.5);
        float r = 0.15f + 0.45f * cycle;
        float g = 0.35f + 0.45f * cycle;
        float b = 0.65f + 0.20f * cycle;
        return new Vector3f(r, g, b);
    }

    private void drawMesh(Chunk chunk) {
        var mesh = chunk.getMesh();
        if (mesh == null || mesh.vertices.length == 0) {
            return;
        }

        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();
        int ebo = glGenBuffers();
        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(mesh.vertices.length);
        vertexBuffer.put(mesh.vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer indexBuffer = BufferUtils.createIntBuffer(mesh.indices.length);
        indexBuffer.put(mesh.indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

        int stride = 8 * Float.BYTES;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, mesh.indices.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
    }

    private int createProgram() {
        int vertexShader = createShader(GL_VERTEX_SHADER,
                "#version 330 core\n" +
                        "layout(location = 0) in vec3 position;\n" +
                        "layout(location = 1) in vec3 normal;\n" +
                        "layout(location = 2) in vec2 uv;\n" +
                        "uniform mat4 projection;\n" +
                        "uniform mat4 view;\n" +
                        "out vec3 fragNormal;\n" +
                        "out vec2 fragUv;\n" +
                        "void main() {\n" +
                        "    fragNormal = normal;\n" +
                        "    fragUv = uv;\n" +
                        "    gl_Position = projection * view * vec4(position, 1.0);\n" +
                        "}");

        int fragmentShader = createShader(GL_FRAGMENT_SHADER,
                "#version 330 core\n" +
                        "in vec3 fragNormal;\n" +
                        "in vec2 fragUv;\n" +
                        "out vec4 outColor;\n" +
                        "uniform sampler2D atlasTexture;\n" +
                        "void main() {\n" +
                        "    vec3 normal = normalize(fragNormal);\n" +
                        "    vec3 lightDir = normalize(vec3(0.6, 0.8, 0.3));\n" +
                        "    float light = max(dot(normal, lightDir), 0.25);\n" +
                        "    vec4 tex = texture(atlasTexture, fragUv);\n" +
                        "    outColor = vec4(tex.rgb * light, tex.a);\n" +
                        "}");

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException(glGetProgramInfoLog(program));
        }
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
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
