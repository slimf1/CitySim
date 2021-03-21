package com.isima.sma.states;

import com.isima.sma.entities.Road;
import com.isima.sma.utils.MTRandom;
import javafx.scene.paint.Color;

public class DefaultState implements RoadState {

    private static final int MAX_COST = 80;
    private static final int MAX_USURY = 1000;

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
        }
    }

    @Override
    public Color getColor(Road road) {
        Color baseColor = Color.RED;
        int clampedCost = Math.max(0, Math.min(MAX_COST, road.cost()));
        for(int i = 0; i < clampedCost; ++i)
            baseColor = baseColor.darker();
        return baseColor;
    }
}
