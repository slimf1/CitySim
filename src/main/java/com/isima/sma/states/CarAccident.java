package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Représente un accident sur une route.
 * Augmente son coût de façon importante.
 * @author Slimane F.
 */
public class CarAccident implements RoadState, Serializable {

    /**
     * La durée par défaut d'un accident
     */
    private static final int DEFAULT_DURATION = 15;
    /**
     * La différence du coût de la route quand
     * il y a un accident
     */
    private static final int COST_DIFFERENCE = 100;

    private static final long serialVersionUID = 6242332304138062867L;

    /**
     * Durée courante de l'accident
     */
    private int duration;

    /**
     * Constructeur par défaut d'un accident,
     * fixe la durée par défaut
     */
    public CarAccident() {
        this(DEFAULT_DURATION);
    }

    /**
     * Constructeur d'un accident
     * @param duration La durée de l'accident
     */
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
