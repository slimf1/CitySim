package com.isima.sma.states;

import com.isima.sma.entities.Road;
import javafx.scene.paint.Color;

public interface RoadState {
    int updateCost(Road road, int initialCost);
    void updateState(Road road);
    Color getColor();
}
