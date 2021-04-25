package com.isima.sma.vehicles;

import com.isima.sma.city.City;
import com.isima.sma.strategies.BfsStrategy;
import com.isima.sma.strategies.DijkstraStrategy;
import com.isima.sma.strategies.PathingStrategy;
import com.isima.sma.utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Classe véhicule.
 * @author Slimane F.
 */
public class Vehicle {

    /**
     * La stratégie part défaut pour créer un nouveau
     * trajet entre deux points de la ville
     */
    private static final PathingStrategy DEFAULT_PATHING_STRATEGY = new DijkstraStrategy();

    /**
     * Chemin qu'emprunte le véhicule
     */
    private Queue<Pair<Integer, Integer>> path;
    /**
     * La stratégie appliquée par le véhicule
     * (pattern strategy)
     */
    private PathingStrategy pathingStrategy;

    /**
     * Constructeur par défaut d'un véhicule
     */
    public Vehicle() {
        this(DEFAULT_PATHING_STRATEGY);
    }

    /**
     * Constructeur d'un véhicule
     * @param pathingStrategy La stratégie à utiliser pour la création
     *                        d'un chemin
     */
    public Vehicle(PathingStrategy pathingStrategy) {
        this.pathingStrategy = pathingStrategy;
        this.path = null;
    }

    /**
     * Setter pour la stratégie de création d'un chemin
     * @param pathingStrategy La nouvelle stratégie du véhicule
     */
    public final void setPathingStrategy(PathingStrategy pathingStrategy) {
        this.pathingStrategy = pathingStrategy;
    }

    /**
     * Fixe le chemin du véhicule
     * @param city La ville
     * @param xDep L'abscisse du point de départ
     * @param yDep L'ordonnée du point de départ
     * @param xDest L'abscisse du point d'arrivée
     * @param yDest L'ordonnée du point d'arrivée
     */
    public void createPath(City city, int xDep, int yDep, int xDest, int yDest) {
        List<Pair<Integer, Integer>> createdPath = pathingStrategy.createPath(city, xDep, yDep, xDest, yDest);
        if (createdPath != null)
            path = new LinkedList<>(createdPath);
    }

    /**
     * Supprime puis retourne la prochaine position
     * qu'emprunte la voiture dans son trajet
     * @return La prochaine position de la voiture en
     * fonction de son trajet
     */
    public Pair<Integer, Integer> pollNextPosition() {
        return path.poll();
    }

    /**
     * Teste si le véhicule est arrivé à destination
     * @return {@code true} si le véhicule est arrivé à
     * sa destination, {@code false} sinon
     */
    public boolean isAtDestination() {
        if (path == null) return true;
        return path.size() < 1;
    }
}
