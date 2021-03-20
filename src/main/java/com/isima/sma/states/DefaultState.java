package com.isima.sma.states;

import com.isima.sma.entities.Road;
import com.isima.sma.utils.MTRandom;
import javafx.scene.paint.Color;

public class DefaultState implements RoadState {

    private static final int MAX_COST = 30;
    private static final int MAX_USURY = 100;

    @Override
    public int updateCost(Road road, int initialCost) {
        return initialCost;
    }

    @Override
    public void updateState(Road road) {
        if (MTRandom.getInstance().nextDouble()
                < (double)road.cost() / MAX_COST) {
            road.setState(new CarAccident());
            return;
        }
        if (MTRandom.getInstance().nextDouble()
                < (double)road.getUsury() / MAX_USURY) {
            road.setState(new RoadWorks(road.getUsury() / 3));
            return;
        }

    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
