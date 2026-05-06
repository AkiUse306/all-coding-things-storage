cmake_minimum_required(VERSION 3.24)
project(acities-engine LANGUAGES CXX)

find_package(JNI REQUIRED)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)

add_library(acities_engine SHARED
    src/Engine.cpp
    src/NativeEngine.cpp
    src/Pathfinding.cpp
)

target_include_directories(acities_engine PUBLIC include ${JNI_INCLUDE_DIRS})

target_compile_features(acities_engine PUBLIC cxx_std_23)

target_link_libraries(acities_engine PRIVATE ${JNI_LIBRARIES})
