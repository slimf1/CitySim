package com.isima.sma.entities;

import com.isima.sma.states.CarAccident;
import com.isima.sma.states.DefaultState;
import com.isima.sma.vehicles.Vehicle;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoadTest {

    @Test
    public void cost() {
        Road road = new Road(0, 0);
        Vehicle v1 = new Vehicle();
        road.addVehicle(v1);
        road.addVehicle(new Vehicle());
        assertEquals(2, (int)road.cost());
        road.setState(new CarAccident());
        assertTrue(road.cost() > 2);
        int tempCost = road.cost();
        road.removeVehicle(v1);
        assertEquals(tempCost - 1, (int)road.cost());
    }

    @Test
    public void usury() {
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Vehicle();
        Road road = new Road(0, 0);
        road.addVehicle(v1);
        road.addVehicle(v2);
        assertEquals(2, road.getUsury());
        road.removeVehicle(v1);
        road.removeVehicle(v2);
        road.addVehicle(new Vehicle());
        assertEquals(3, road.getUsury());
    }

    @Test
    public void conRepresentation() {
        Road road = new Road(0, 0);
        assertEquals("0", road.conRepresentation());
        road.setState(new CarAccident());
        assertTrue(Integer.parseInt(road.conRepresentation()) > 0);
    }

    @Test
    public void compareTo() {
        Road r1 = new Road(0, 0);
        Road r2 = new Road(0, 0);
        r1.setState(new CarAccident());
        assertTrue(r1.compareTo(r2) > 0);
        r2.setState(new CarAccident());
        assertEquals(0, r1.compareTo(r2));
    }
}