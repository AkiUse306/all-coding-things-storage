package com.acities.components;

import com.acities.ecs.Component;

public class PositionComponent implements Component {
    public float x;
    public float y;
    public float z;

    public PositionComponent(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
