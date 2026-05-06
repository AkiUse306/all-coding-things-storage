package com.acities.ecs;

public abstract class SystemBase {
    public abstract void update(double deltaSeconds, EntityManager entityManager);
}
