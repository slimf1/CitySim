package com.isima.sma.states;

import com.isima.sma.entities.Road;

public class CarAccident implements RoadState {

    private static int DEFAULT_DURATION = 3;
    private static int COST_DIFFERENCE = 500;

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
}
