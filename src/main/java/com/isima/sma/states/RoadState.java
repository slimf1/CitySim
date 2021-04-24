package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

/**
 * Représente un état d'une route.
 * Application du design pattern State.
 */
public interface RoadState {
    /**
     * Coût effectif d'une route en fonction
     * de son état
     * @param road La route
     * @param initialCost Le coût initial de la route
     * @return Le coût effectif calculé
     */
    int updateCost(Road road, int initialCost);

    /**
     * Met à jour l'état d'une route en fonction
     * de son état actuel
     * @param road La route
     */
    void updateState(Road road);

    /**
     * La couleur d'une route déterminée
     * par son état
     * @param road La route
     * @return La couleur d'une route
     */
    Color getColor(Road road);
}
