package com.isima.sma.states;

import com.isima.sma.entities.Road;
import com.isima.sma.utils.MTRandom;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * L'état par défaut d'une route. Ne donne pas lieu
 * à d'effets particuliers.
 * @author Slimane F. et Barthélemy J.
 */
public class DefaultState implements RoadState {

    /**
     * Le coût maximal pour le calcul des probabilités
     */
    private static final int MAX_COST = 100;
    /**
     * L'usure maximale pour le calcul des probabilités
     */
    private static final int MAX_USURY = 3000;

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
                < Math.pow(((double)road.cost() / MAX_COST)+ 0.6 * (double)road.getUsury() / MAX_USURY, 3)) {
            road.setState(new CarAccident());
            return;
        }
        if (MTRandom.getInstance().nextDouble()
                < Math.pow((double)road.getUsury() / MAX_USURY, 3)) {
            road.setState(new RoadWorks(road.getUsury() / (MAX_USURY/ 40)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor(Road road) {

        int[] costLevel = {0, 1, 3, 5, 8, 10, 15};
        int i = 0;
        while(i < costLevel.length && road.cost() >= costLevel[i]) {
            i++;
        }
        return i == 1 ? Color.BLACK
                      : Color.hsb(0, 1, 0.5 + (i - 1)/12.0);
    }
}
