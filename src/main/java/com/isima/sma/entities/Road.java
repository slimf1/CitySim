package com.isima.sma.entities;

import com.isima.sma.states.DefaultState;
import com.isima.sma.states.RoadState;
import com.isima.sma.vehicles.Vehicle;
import javafx.scene.paint.Paint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Route de la ville, itérable sur les
 * véhicules se déplaçant dessus
 * @author Slimane F.
 */
public class Road extends Entity implements Iterable<Vehicle>, Comparable<Road> {

    /**
     * Le coût maximal d'une route
     */
    private static final int MAX_COST = 10;
    /**
     * L'état par défaut d'une route, statique car
     * n'a pas d'état particulier
     */
    public static final RoadState DEFAULT_STATE = new DefaultState();

    private static final long serialVersionUID = -2966220689110826407L;

    /**
     * Usure de la route
     */
    private int usury;
    /**
     * Les véhicules sur la route
     */
    private Queue<Vehicle> vehicles;
    /**
     * L'état de la route (pattern state)
     */
    private RoadState state;
    /**
     * Indique un arrêt de bus
     */
    private boolean busStop;

    /**
     * Constructeur d'une route
     * @param x L'abscisse du point de la route
     * @param y L'ordonnée du point de la route
     */
    public Road(int x, int y) {
        super(x, y);
        this.usury = 0;
        this.vehicles = new LinkedList<>();
        this.state = DEFAULT_STATE;
        this.busStop = false;
    }

    /**
     * Indique si la route est dotée d'un
     * arrêt de bus
     * @return {@code true} si la route est un arrêt
     * de bus, {@code false} sinon
     */
    public final boolean isBusStop() {
        return busStop;
    }

    /**
     * Fixe un arrêt de bus sur la route
     * @param busStop Le booléen qui indique s'il y a
     *                un arrêt de bus sur la route
     */
    public final void setBusStop(boolean busStop) {
        this.busStop = busStop;
    }

    /**
     * Fixe l'état d'une route
     * @implNote Application du pattern state
     * @param state Le nouvel état de la route
     */
    public final void setState(RoadState state) {
        this.state = state;
    }

    /**
     * Ajoute un véhicule sur la route
     * @param vehicle Le véhicle entrant
     */
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        usury++;
    }

    /**
     * Retire un véhicule de la route
     * @param vehicle Le véhicule à rétirer
     */
    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    /**
     * Le coût de l'emprunt d'une route
     * @return Le coût d'une route
     */
    public Integer cost() {
        return state.updateCost(this, vehicles.size());
    }

    /**
     * L'usure d'une route
     * @return La valeur de l'usure de la route
     */
    public final int getUsury() {
        return usury;
    }

    /**
     * Fixe l'usure de la route
     * @param usury La nouvelle valeur de l'usure
     */
    public final void setUsury(int usury) {
        this.usury = usury;
    }

    /**
     * Spécifie que l'entité est une route
     * @return {@code true}
     */
    @Override
    public boolean isRoad() {
        return true;
    }

    /**
     * Représentation d'une route
     * @return La représentation d'une route dans
     * une chaîne
     */
    @Override
    public String toString() {
        return String.format("Road [usury=%d, cost=%d]", usury, cost());
    }

    /**
     * L'itérateur pour une route. Permet
     * d'itérer sur les différentes voitures
     * qui la compose.
     * @return L'itérateur des voitures de la route
     */
    @Override
    public Iterator<Vehicle> iterator() {
        return vehicles.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String conRepresentation() {
        return cost().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Paint fxRepresentation() {
        return state.getColor(this);
    }

    /**
     * Compare deux routes
     * @param o L'autre route
     * @return 1 si son coût est moins élevé,
     * 0 si les coûts sont égaux,
     * -1 sinon
     */
    @Override
    public int compareTo(Road o) {
        return cost().compareTo(o.cost());
    }

    /**
     * Met à jour l'état de la route
     */
    public void updateState() {
        state.updateState(this);
    }
}
