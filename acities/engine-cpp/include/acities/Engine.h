#pragma once

namespace acities {

class Engine {
public:
    Engine();
    ~Engine();

    void initialize();
    void tick(double deltaSeconds);
    void shutdown();
};

} // namespace acities
