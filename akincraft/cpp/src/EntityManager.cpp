#include "EntityManager.h"
#include "Entity.h"
#include <algorithm>
#include <glm/gtc/type_ptr.hpp>

void EntityManager::update(float deltaTime) {
    for (auto& entity : entities) {
        entity->update(deltaTime);
    }
    
    // Remove dead entities
    entities.erase(
        std::remove_if(entities.begin(), entities.end(),
            [](const std::shared_ptr<Entity>& e) { return e->isDead_(); }),
        entities.end()
    );
}

void EntityManager::render() {
    for (auto& entity : entities) {
        entity->render();
    }
}

void EntityManager::addEntity(std::shared_ptr<Entity> entity) {
    entities.push_back(entity);
}

void EntityManager::removeEntity(std::shared_ptr<Entity> entity) {
    entities.erase(
        std::remove(entities.begin(), entities.end(), entity),
        entities.end()
    );
}

std::vector<std::shared_ptr<Entity>> EntityManager::getEntities() const {
    return entities;
}

std::vector<std::shared_ptr<Entity>> EntityManager::getEntitiesNear(
    const glm::vec3& pos, float radius) {
    std::vector<std::shared_ptr<Entity>> nearby;
    for (const auto& entity : entities) {
        if (glm::distance(entity->getPosition(), pos) < radius) {
            nearby.push_back(entity);
        }
    }
    return nearby;
}
