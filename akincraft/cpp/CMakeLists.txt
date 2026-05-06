cmake_minimum_required(VERSION 3.24)
project(akincraft_cpp LANGUAGES CXX)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include(FetchContent)

FetchContent_Declare(
  glfw
  GIT_REPOSITORY https://github.com/glfw/glfw.git
  GIT_TAG 3.3.10
)

FetchContent_Declare(
  glm
  GIT_REPOSITORY https://github.com/g-truc/glm.git
  GIT_TAG 0.9.9.8
)

FetchContent_MakeAvailable(glfw glm)

find_package(OpenGL REQUIRED)

file(GLOB CPP_SOURCES src/*.cpp)
add_executable(akincraft_cpp ${CPP_SOURCES})

target_include_directories(akincraft_cpp PRIVATE src)
target_include_directories(akincraft_cpp SYSTEM PRIVATE ${glm_SOURCE_DIR})

target_link_libraries(akincraft_cpp PRIVATE glfw OpenGL::GL)
if (APPLE)
  find_library(COCOA_LIBRARY Cocoa REQUIRED)
  find_library(IOKIT_LIBRARY IOKit REQUIRED)
  find_library(COREVIDEO_LIBRARY CoreVideo REQUIRED)
  find_library(FOUNDATION_LIBRARY Foundation REQUIRED)
  target_link_libraries(akincraft_cpp PRIVATE ${COCOA_LIBRARY} ${IOKIT_LIBRARY} ${COREVIDEO_LIBRARY} ${FOUNDATION_LIBRARY})
endif()
