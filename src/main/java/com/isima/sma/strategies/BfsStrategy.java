package com.isima.sma.strategies;

import com.isima.sma.city.City;
import com.isima.sma.entities.Road;
import com.isima.sma.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Stratégie de pathfinding utilisant l'algorithme de
 * parcours en largeur
 */
public class BfsStrategy implements PathingStrategy {
    /**
     * {@inheritDoc}
     * Implémentation par l'algorithme de parcours en largeur
     */
    @Override
    public List<Pair<Integer, Integer>> createPath(City city, int xDep, int yDep, int xDest, int yDest) {
        if (!city.isRoad(xDep, yDep))
            throw new IllegalArgumentException("Starting point is not a road");
        if (!city.isRoad(xDest, yDest))
            throw new IllegalArgumentException("Destination point is not a road");

        Comparator<Pair<Integer, Integer>> comparator = Comparator.comparing(a -> ((Road)city.getEntityAt(a.getFirst(), a.getSecond())));
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        Set<Pair<Integer, Integer>> visitedNodes = new HashSet<>();
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parents = new HashMap<>();
        Queue<Pair<Integer, Integer>> queue = new PriorityQueue<>(comparator);
        boolean atDestination = false;

        visitedNodes.add(new Pair<>(xDep, yDep));
        queue.add(new Pair<>(xDep, yDep));

        while (!queue.isEmpty() && !atDestination) {
            Pair<Integer, Integer> currentRoad = queue.poll();

            if (currentRoad.getFirst() == xDest && currentRoad.getSecond() == yDest) {
                atDestination = true;
            } else {
                Set<Pair<Integer, Integer>> adjacentRoads = city.getAdjacentRoads(currentRoad.getFirst(),
                        currentRoad.getSecond());
                for (Pair<Integer, Integer> adjacentRoad : adjacentRoads) {
                    if (!visitedNodes.contains(adjacentRoad)) {
                        visitedNodes.add(adjacentRoad);
                        queue.add(adjacentRoad);
                        parents.put(adjacentRoad, currentRoad);
                    }
                }
            }
        }

        if (atDestination) {
            Pair<Integer, Integer> temp = new Pair<>(xDest, yDest);
            do {
                path.add(temp);
                temp = parents.get(temp);
            } while (parents.containsKey(temp));
            Collections.reverse(path);
            return path;
        } else {
            return null;
        }
    }
}
