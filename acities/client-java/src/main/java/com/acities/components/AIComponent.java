package com.acities.components;

import com.acities.ecs.Component;

public class AIComponent implements Component {
    public enum State {
        IDLE,
        MOVING,
        WORKING,
        SOCIALIZING,
        RETURNING_HOME
    }

    public State currentState;
    public float targetX;
    public float targetY;

    public AIComponent(State state, float targetX, float targetY) {
        this.currentState = state;
        this.targetX = targetX;
        this.targetY = targetY;
    }
}
