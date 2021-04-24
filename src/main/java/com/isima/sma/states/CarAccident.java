package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Représente un accident sur une route.
 * Augmente son coût de façon importante.
 */
public class CarAccident implements RoadState, Serializable {

    private static final int DEFAULT_DURATION = 15; // La durée par défaut
    private static final int COST_DIFFERENCE = 100; // La différence dans le coût

    private static final long serialVersionUID = 6242332304138062867L;

    private int duration; // Durée actuelle

    public CarAccident() {
        this(DEFAULT_DURATION);
    }

    public CarAccident(int duration) {
        this.duration = duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateCost(Road road, int initialCost) {
        return initialCost + COST_DIFFERENCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState(Road road) {
        if (--duration <= 0) {
            road.setState(Road.DEFAULT_STATE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor(Road road) {
        return Color.MAGENTA;
    }
}
