package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class CarAccident implements RoadState, Serializable {

    private static final int DEFAULT_DURATION = 1;
    private static final int COST_DIFFERENCE = 100;

    private static final long serialVersionUID = 6242332304138062867L;

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
        if (--duration <= 0) {
            road.setState(Road.DEFAULT_STATE);
        }
    }

    @Override
    public Color getColor(Road road) {
        return Color.MAGENTA;
    }
}
