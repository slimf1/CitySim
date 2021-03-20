package com.isima.sma.states;

import com.isima.sma.entities.Road;

public interface RoadState {
    int updateCost(Road road, int initialCost);
    void updateState(Road road);
}
