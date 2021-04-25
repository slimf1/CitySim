package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Représente les travaux sur une route.
 * Permet de décrémenter l'usure d'une route ;
 * la durée de ces travaux dépend de l'usure.
 * Augmente le coût de façon linéaire sur l'usure
 * de la route.
 * @author Slimane F.
 */
public class RoadWorks implements RoadState, Serializable {

    /**
     * La durée par défaut des travaux
     */
    private static final int DEFAULT_DURATION = 10;
    /**
     * Le facteur pour le calcul du coût
     */
    private static final int FACTOR = 2;

    private static final long serialVersionUID = 6583558997930872437L;

    /**
     * La durée restante des travaux
     */
    private int duration;

    /**
     * Constructeur de l'état travaux
     */
    public RoadWorks() {
        this(DEFAULT_DURATION);
    }

    /**
     * Constructeur de l'état travaux
     * @param duration La durée des travaux
     */
    public RoadWorks(int duration) {
        this.duration = duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateCost(Road road, int initialCost) {
        return initialCost + duration * FACTOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState(Road road) {
        if (duration-- <= 0) {
            road.setUsury(0);
            road.setState(Road.DEFAULT_STATE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor(Road road) {
        return Color.ORANGE;
    }
}
