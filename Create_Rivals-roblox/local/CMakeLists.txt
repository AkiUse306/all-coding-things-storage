cmake_minimum_required(VERSION 3.10)
project(RivalsClient)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# No external dependencies needed!
# File-based storage using standard C++ library

# Add the main executable
add_executable(rivals-client main.cpp)

# Optional: If nlohmann_json is available, include it
find_package(nlohmann_json 3.2.0 QUIET)
if(nlohmann_json_FOUND)
    target_link_libraries(rivals-client PRIVATE nlohmann_json::nlohmann_json)
    target_compile_definitions(rivals-client PRIVATE NLOHMANN_JSON_AVAILABLE)
    message(STATUS "nlohmann_json found - JSON support enabled")
else()
    message(STATUS "nlohmann_json not found - using simple storage format")
endif()