#include "acities/Engine.h"
#include <iostream>

namespace acities {

Engine::Engine() = default;
Engine::~Engine() = default;

void Engine::initialize() {
    std::cout << "[acities-engine] Initializing native engine module...\n";
}

void Engine::tick(double deltaSeconds) {
    // TODO: Run physics, pathfinding and simulation ticks.
}

void Engine::shutdown() {
    std::cout << "[acities-engine] Shutting down native engine module...\n";
}

} // namespace acities
