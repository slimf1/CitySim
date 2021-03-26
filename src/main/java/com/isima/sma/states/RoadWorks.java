package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class RoadWorks implements RoadState, Serializable {

    private static final int DEFAULT_DURATION = 10;
    private static final int FACTOR = 2;

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
        if (duration-- <= 0) {
            road.setUsury(0);
            road.setState(Road.DEFAULT_STATE);
        }
    }

    @Override
    public Color getColor(Road road) {
        return Color.ORANGE;
    }
}
