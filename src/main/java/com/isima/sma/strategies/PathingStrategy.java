package com.isima.sma.strategies;

import com.isima.sma.city.City;
import com.isima.sma.utils.Pair;

import java.util.List;

/**
 * Stratégie de pathfinding pour les
 * véhicules de la simulation
 * @author Slimane F.
 */
public interface PathingStrategy extends java.io.Serializable {
    /**
     * Créé un chemin entre deux points de la ville.
     * @param city La ville
     * @param xDep L'abscisse du point de départ
     * @param yDep L'ordonnée du point de départ
     * @param xDest L'abscisse du point d'arrivée
     * @param yDest L'ordonnée du point d'arrivée
     * @return Un chemin entre le point de départ et le point
     * d'arrivée
     */
    List<Pair<Integer, Integer>> createPath(City city, int xDep, int yDep, int xDest, int yDest);
}
