package com.isima.sma.states;

import com.isima.sma.entities.Road;
import com.isima.sma.utils.MTRandom;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * L'état par défaut d'une route. Ne donne pas lieu
 * à d'effets particuliers.
 */
public class DefaultState implements RoadState, Serializable {

    private static final int MAX_COST = 80; // Le coût maximal d'une route
    private static final int MAX_USURY = 1000; // L'usure maximale d'une route

    private static final long serialVersionUID = 7182582293063989705L;

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateCost(Road road, int initialCost) {
        return initialCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState(Road road) {
        if (road.cost() >0 && MTRandom.getInstance().nextDouble()
                < Math.pow(((double)road.cost() / MAX_COST)+(double)road.getUsury() / MAX_USURY, 3)) {
            System.out.println("Accident :\tcoût: " + road.cost() + "\tusure: "+road.getUsury() +"\tproba: " + Math.pow(((double)road.cost() / MAX_COST)+(double)road.getUsury() / MAX_USURY, 2));
            road.setState(new CarAccident());
            return;
        }
        if (MTRandom.getInstance().nextDouble()
                < Math.pow((double)road.getUsury() / MAX_USURY, 3)) {
            System.out.println("Travaux:\tusure: "+road.getUsury()+"\tproba: "+Math.pow((double)road.getUsury() / MAX_USURY, 2));
            road.setState(new RoadWorks(road.getUsury() / 3));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor(Road road) {

        int [] costLevel = {0, 1, 3, 5, 8, 10, 15};
        Color [] colorLevel = new Color[costLevel.length];
        colorLevel[0] = Color.BLACK;
        for (int i=0; i < costLevel.length-1; i++){
            colorLevel[i+1] = Color.hsb(0, 1, 0.5+i/12.0);
        }
        int i = 0;
        while(i < costLevel.length && road.cost() >= costLevel[i]){
            i++;
        }
        return colorLevel[i-1];

    }
}
