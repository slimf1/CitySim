package com.isima.sma.strategies;

import com.isima.sma.city.City;
import com.isima.sma.entities.Road;
import com.isima.sma.utils.Pair;

import java.util.*;

/**
 * Stratégie de pathfinding utilisant l'algorithme
 * de Dijkstra
 * @author Slimane F.
 */
public class DijkstraStrategy implements PathingStrategy, java.io.Serializable {

    private static final long serialVersionUID = 1223215659417066635L;

    /**
     * {@inheritDoc}
     * Implémentation par l'algorithme de Dijkstra
     */
    @Override
    public List<Pair<Integer, Integer>> createPath(City city, int xDep, int yDep, int xDest, int yDest) {
        if (!city.isRoad(xDep, yDep))
            throw new IllegalArgumentException("Starting point is not a road");
        if (!city.isRoad(xDest, yDest))
            throw new IllegalArgumentException("Destination point is not a road");

        List<Road> roads = city.getRoads();
        Map<Pair<Integer, Integer>, Double> distance = new HashMap<>();
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parents = new HashMap<>();
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        Set<Pair<Integer, Integer>> edges = new HashSet<>();

        for (Road road : roads) {
            edges.add(new Pair<>(road.getX(), road.getY()));
            distance.put(new Pair<>(road.getX(), road.getY()),
                         Double.POSITIVE_INFINITY);
        }
        distance.put(new Pair<>(xDep, yDep), 0.0);

        while (!edges.isEmpty()) {
            Pair<Integer, Integer> minRoad = edges
                    .stream()
                    .min(Comparator.comparingDouble(distance::get))
                    .orElseThrow(NoSuchElementException::new);
            edges.remove(minRoad);
            Set<Pair<Integer, Integer>> neighbors = city
                    .getAdjacentRoads(minRoad.getFirst(),
                                      minRoad.getSecond());
            for(Pair<Integer, Integer> neighbor : neighbors) {
                if (!edges.contains(neighbor)) continue;
                double alt = distance.get(minRoad) + ((Road)city
                        .getEntityAt(minRoad.getFirst(),
                                     minRoad.getSecond()))
                        .cost();
                if (alt < distance.get(neighbor)) {
                    distance.put(neighbor, alt);
                    parents.put(neighbor, minRoad);
                }
            }
        }

        Pair<Integer, Integer> temp = new Pair<>(xDest, yDest);
        do {
            path.add(temp);
            temp = parents.get(temp);
        } while (parents.containsKey(temp));
        Collections.reverse(path);
        return path;
    }
}
