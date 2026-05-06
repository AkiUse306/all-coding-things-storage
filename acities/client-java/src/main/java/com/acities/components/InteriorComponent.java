package com.acities.components;

import com.acities.ecs.Component;

public class InteriorComponent implements Component {
    public final String[] rooms;
    public final String description;

    public InteriorComponent(String[] rooms, String description) {
        this.rooms = rooms;
        this.description = description;
    }
}