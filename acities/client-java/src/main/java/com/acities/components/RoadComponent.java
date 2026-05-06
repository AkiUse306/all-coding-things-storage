package com.acities.components;

import com.acities.ecs.Component;

public class RoadComponent implements Component {
    public final float width;
    public final boolean isHorizontal;

    public RoadComponent(float width, boolean isHorizontal) {
        this.width = width;
        this.isHorizontal = isHorizontal;
    }
}