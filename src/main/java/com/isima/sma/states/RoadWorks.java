package com.isima.sma.states;

import com.isima.sma.entities.Road;

public class RoadWorks implements RoadState {

    private static int DEFAULT_DURATION = 10;
    private static int FACTOR = 2;

    private int duration;

    public RoadWorks() {
        this(DEFAULT_DURATION);
    }

    public RoadWorks(int duration) {
        this.duration = duration;
    }

    @Override
    public int updateCost(Road road, int initialCost) {
        return initialCost + duration * FACTOR;
    }

    @Override
    public void updateState(Road road) {
        if (duration-- <= 0) { // Travaux finis
            road.setUsury(0);
            road.setState(Road.DEFAULT_STATE);
        }
    }
}
