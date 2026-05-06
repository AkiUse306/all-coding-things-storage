package com.acities.components;

import com.acities.ecs.Component;

public class BuildingComponent implements Component {
    public final int width;
    public final int height;
    public final String type; // e.g., "residential", "commercial", "industrial"

    public BuildingComponent(int width, int height, String type) {
        this.width = width;
        this.height = height;
        this.type = type;
    }
}