package com.isima.sma;

import com.isima.sma.utils.Pair;

import java.util.List;

public interface PathingStrategy {
    List<Pair<Integer, Integer>> createPath(City city, int xDep, int yDep, int xDest, int yDest);
}
