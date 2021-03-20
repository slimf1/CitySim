package com.isima.sma.entities;

import com.isima.sma.vehicles.Vehicle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Road extends Entity implements Iterable<Vehicle>, Comparable<Road> {
    // State pour cost

    private static final int MAX_COST = 10;

    private int usury;
    private Queue<Vehicle> vehicles;

    public Road() {
        super();
        this.usury = 0;
        this.vehicles = new LinkedList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        usury++;
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public Integer cost() {
        Integer cost = vehicles.size();
        // State
        return cost;
    }

    public final int getUsury() {
        return usury;
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
        Color baseColor = Color.RED;
        int clampedCost = Math.max(0, Math.min(MAX_COST, cost()));
        for(int i = 0; i < clampedCost; ++i)
            baseColor = baseColor.darker();
        return baseColor;
    }

    @Override
    public int compareTo(Road o) {
        return cost().compareTo(o.cost());
    }
}
