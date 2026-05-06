#pragma once

#include <vector>
#include <memory>
#include <glm/glm.hpp>

class Entity;

/**
 * Manages all entities in the world
 */
class EntityManager {
private:
    std::vector<std::shared_ptr<Entity>> entities;
    
public:
    void update(float deltaTime);
    void render();
    
    void addEntity(std::shared_ptr<Entity> entity);
    void removeEntity(std::shared_ptr<Entity> entity);
    std::vector<std::shared_ptr<Entity>> getEntities() const;
    std::vector<std::shared_ptr<Entity>> getEntitiesNear(const glm::vec3& pos, float radius);
    
    int getEntityCount() const { return entities.size(); }
};
