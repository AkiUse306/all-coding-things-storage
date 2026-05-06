package com.acities.components;

import com.acities.ecs.Component;

public class RenderComponent implements Component {
    public final float red;
    public final float green;
    public final float blue;
    public final float size;

    public RenderComponent(float red, float green, float blue, float size) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.size = size;
    }
}
