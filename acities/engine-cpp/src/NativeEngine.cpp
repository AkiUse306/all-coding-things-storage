#include "acities/Engine.h"
#include "acities/Pathfinding.h"
#include <jni.h>
#include <iostream>
#include <vector>

static acities::Engine nativeEngine;
static acities::Pathfinding* pathfinding = nullptr;

extern "C" {

JNIEXPORT void JNICALL Java_com_acities_engine_NativeEngine_initialize(JNIEnv* env, jclass clazz) {
    std::cout << "[acities-engine] JNI initialize called\n";
    nativeEngine.initialize();
    if (!pathfinding) {
        pathfinding = new acities::Pathfinding(1280, 720); // Assuming grid size matches window
    }
}

JNIEXPORT void JNICALL Java_com_acities_engine_NativeEngine_tick(JNIEnv* env, jclass clazz, jdouble deltaSeconds) {
    nativeEngine.tick(static_cast<double>(deltaSeconds));
}

JNIEXPORT void JNICALL Java_com_acities_engine_NativeEngine_shutdown(JNIEnv* env, jclass clazz) {
    std::cout << "[acities-engine] JNI shutdown called\n";
    nativeEngine.shutdown();
    if (pathfinding) {
        delete pathfinding;
        pathfinding = nullptr;
    }
}

JNIEXPORT void JNICALL Java_com_acities_engine_NativeEngine_setObstacle(JNIEnv* env, jclass clazz, jint x, jint y, jboolean obstacle) {
    if (pathfinding) {
        pathfinding->setObstacle(x, y, obstacle);
    }
}

JNIEXPORT jfloatArray JNICALL Java_com_acities_engine_NativeEngine_findPath(JNIEnv* env, jclass clazz, jfloat startX, jfloat startY, jfloat goalX, jfloat goalY) {
    if (!pathfinding) {
        return nullptr;
    }

    acities::Vec2 start(startX, startY);
    acities::Vec2 goal(goalX, goalY);
    std::vector<acities::Vec2> path = pathfinding->findPath(start, goal);

    if (path.empty()) {
        return nullptr;
    }

    jfloatArray result = env->NewFloatArray(path.size() * 2);
    jfloat* buffer = new jfloat[path.size() * 2];
    for (size_t i = 0; i < path.size(); ++i) {
        buffer[i * 2] = path[i].x;
        buffer[i * 2 + 1] = path[i].y;
    }
    env->SetFloatArrayRegion(result, 0, path.size() * 2, buffer);
    delete[] buffer;
    return result;
}

}
