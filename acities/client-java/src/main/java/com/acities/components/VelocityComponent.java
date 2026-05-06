package com.acities.components;

import com.acities.ecs.Component;

public class VelocityComponent implements Component {
    public float dx;
    public float dy;
    public float dz;

    public VelocityComponent(float dx, float dy, float dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
}
