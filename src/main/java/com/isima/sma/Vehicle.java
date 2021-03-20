package com.isima.sma;

import com.isima.sma.strategies.BfsStrategy;
import com.isima.sma.utils.Pair;

import java.util.LinkedList;
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
    }

    public final void setPathingStrategy(PathingStrategy pathingStrategy) {
        this.pathingStrategy = pathingStrategy;
    }

    public void createPath(City city, int xDep, int yDep, int xDest, int yDest) {
        path = new LinkedList<>(pathingStrategy.createPath(city, xDep, yDep, xDest, yDest));
    }

    public Pair<Integer, Integer> pollNextPosition() {
        return path.poll();
    }

    public boolean isAtDestination() {
        return path.size() <= 1;
    }


}
