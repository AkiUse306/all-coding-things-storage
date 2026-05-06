package com.acities.ecs;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Entity {
    private final int id;
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public <T extends Component> void addComponent(T component) {
        components.put(component.getClass(), component);
    }

    public <T extends Component> Optional<T> getComponent(Class<T> clazz) {
        return Optional.ofNullable(clazz.cast(components.get(clazz)));
    }

    public <T extends Component> boolean hasComponent(Class<T> clazz) {
        return components.containsKey(clazz);
    }

    public void removeComponent(Class<? extends Component> clazz) {
        components.remove(clazz);
    }
}
