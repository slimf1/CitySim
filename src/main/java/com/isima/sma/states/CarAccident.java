package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

public class CarAccident implements RoadState {

    private static final int DEFAULT_DURATION = 3;
    private static final int COST_DIFFERENCE = 100;

    private int duration;

    public CarAccident() {
        this(DEFAULT_DURATION);
    }

    public CarAccident(int duration) {
        this.duration = duration;
    }

    @Override
    public int updateCost(Road road, int initialCost) {
        return initialCost + COST_DIFFERENCE;
    }

    @Override
    public void updateState(Road road) {
        if (duration-- <= 0) {
            road.setState(Road.DEFAULT_STATE);
        }
    }

    @Override
    public Color getColor() {
        return Color.DEEPPINK;
    }
}
