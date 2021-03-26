package com.isima.sma.vehicles;

import com.isima.sma.city.City;
import com.isima.sma.strategies.BfsStrategy;
import com.isima.sma.strategies.PathingStrategy;
import com.isima.sma.utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Vehicle {

    private static final PathingStrategy DEFAULT_PATHING_STRATEGY = new BfsStrategy();

    private Queue<Pair<Integer, Integer>> path;
    private PathingStrategy pathingStrategy;

    public Vehicle() {
        this(DEFAULT_PATHING_STRATEGY);
    }

    public Vehicle(PathingStrategy pathingStrategy) {
        this.pathingStrategy = pathingStrategy;
        this.path = null;
    }

    public final void setPathingStrategy(PathingStrategy pathingStrategy) {
        this.pathingStrategy = pathingStrategy;
    }

    public void createPath(City city, int xDep, int yDep, int xDest, int yDest) {
        List<Pair<Integer, Integer>> createdPath = pathingStrategy.createPath(city, xDep, yDep, xDest, yDest);
        if (createdPath != null)
            path = new LinkedList<>(createdPath);
    }

    public Pair<Integer, Integer> pollNextPosition() {
        return path.poll();
    }

    public boolean isAtDestination() {
        if (path == null) return true;
        return path.size() < 1;
    }
}
