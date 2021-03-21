package com.isima.sma.entities;

import com.isima.sma.states.DefaultState;
import com.isima.sma.states.RoadState;
import com.isima.sma.vehicles.Vehicle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Road extends Entity implements Iterable<Vehicle>, Comparable<Road> {

    private static final int MAX_COST = 10;
    public static final RoadState DEFAULT_STATE = new DefaultState();

    private int usury;
    private Queue<Vehicle> vehicles;
    private RoadState state;
    private boolean busStop;

    public Road(int x, int y) {
        super(x, y);
        this.usury = 0;
        this.vehicles = new LinkedList<>();
        this.state = DEFAULT_STATE;
        this.busStop = false;
    }

    public final boolean isBusStop() {
        return busStop;
    }

    public final void setBusStop(boolean busStop) {
        this.busStop = busStop;
    }

    public final void setState(RoadState state) {
        this.state = state;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        usury++;
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public Integer cost() {
        return state.updateCost(this, vehicles.size());
    }

    public final int getUsury() {
        return usury;
    }

    public final void setUsury(int usury) {
        this.usury = usury;
    }

    @Override
    public String toString() {
        return String.format("Road [usury=%d, cost=%d]", usury, cost());
    }

    @Override
    public Iterator<Vehicle> iterator() {
        return vehicles.iterator();
    }

    @Override
    public String conRepresentation() {
        return cost().toString();
    }

    @Override
    public Paint fxRepresentation() {
        return state.getColor(this);
    }

    @Override
    public int compareTo(Road o) {
        return cost().compareTo(o.cost());
    }

    public void updateState() {
        state.updateState(this);
    }
}
