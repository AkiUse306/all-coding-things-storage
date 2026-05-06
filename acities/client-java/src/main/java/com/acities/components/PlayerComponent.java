package com.acities.components;

import com.acities.ecs.Component;

public class PlayerComponent implements Component {
    public final String username;
    public final String faction;

    public PlayerComponent(String username, String faction) {
        this.username = username;
        this.faction = faction;
    }
}
