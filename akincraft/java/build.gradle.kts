plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.lwjgl:lwjgl:3.4.0")
    implementation("org.lwjgl:lwjgl-glfw:3.4.0")
    implementation("org.lwjgl:lwjgl-opengl:3.4.0")
    implementation("org.joml:joml:1.10.5")
    runtimeOnly("org.lwjgl:lwjgl:3.4.0:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.4.0:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-opengl:3.4.0:natives-linux")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("akincraft.App")
}
