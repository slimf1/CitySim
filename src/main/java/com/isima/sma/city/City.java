package com.isima.sma.city;

import com.isima.sma.entities.Entity;
import com.isima.sma.entities.Road;
import com.isima.sma.entities.Zone;
import com.isima.sma.entities.ZoneType;
import com.isima.sma.utils.Clock;
import com.isima.sma.utils.MTRandom;
import com.isima.sma.utils.Pair;
import com.isima.sma.utils.TimeOfDay;
import com.isima.sma.vehicles.Vehicle;

import java.io.*;
import java.util.*;

public class City implements Serializable {

    private static final int DEFAULT_WIDTH = 15;
    private static final int DEFAULT_HEIGHT = 10;

    private Entity[] grid;
    private List<Road> roads;
    private Map<ZoneType, List<Zone>> zones;
    private int width;
    private int height;
    private TimeOfDay timeOfDay;
    private HashMap<TimeOfDay, Pair<Integer, Integer>> timesTOD;

    public City() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public City(int width, int height) {
        this.grid = new Entity[width * height];
        this.roads = new ArrayList<>();
        this.zones = new HashMap<>();
        this.width = width;
        this.height = height;
        this.timeOfDay = TimeOfDay.DAWN;

        // Building period tools
        float part = Clock.TICK_MAX / 30;
        timesTOD = new HashMap<>();
        timesTOD.putIfAbsent(TimeOfDay.DAWN, new Pair<Integer, Integer>((int)(4*part+1), (int)(6*part)));
        timesTOD.putIfAbsent(TimeOfDay.MORNING, new Pair<Integer, Integer>((int)(6*part+1), (int)(12*part)));
        timesTOD.putIfAbsent(TimeOfDay.MIDDAY, new Pair<Integer, Integer>((int)(12*part+1),(int) (17*part)));
        timesTOD.putIfAbsent(TimeOfDay.AFTERNOON, new Pair<Integer, Integer>((int)(17*part+1), (int)(22*part)));
        timesTOD.putIfAbsent(TimeOfDay.DUSK, new Pair<Integer, Integer>((int)(22*part+1), (int)(25*part)));
        timesTOD.putIfAbsent(TimeOfDay.NIGHT, new Pair<Integer, Integer>((int)(25*part+1), (int)(4*part)));
    }

    public Entity getEntityAt(int x, int y) {
        if (isInsideGrid(x, y))
            return grid[x * height + y];
        return null;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final void setTimeOfDay(){
        int tickMax = Clock.TICK_MAX;
        int currentTime = Clock.getInstance().getTime();
        TimeOfDay newTOD = null;
        if(this.timeOfDay == null || currentTime == timesTOD.get(TimeOfDay.NIGHT).getFirst() ){
            this.timeOfDay = TimeOfDay.NIGHT;
        }
        else{
            if(currentTime < timesTOD.get(TimeOfDay.NIGHT).getFirst()){
                if(currentTime > timesTOD.get(timeOfDay).getSecond()){
                    for(Map.Entry<TimeOfDay, Pair<Integer, Integer>> entry : timesTOD.entrySet()){
                        if(currentTime >= entry.getValue().getFirst() && currentTime <= entry.getValue().getSecond()){
                            newTOD = entry.getKey();
                        }
                    }
                    this.timeOfDay = newTOD;
                }
            }
        }
    }

    public final TimeOfDay getTimeOfDay(){
        return this.timeOfDay;
    }

    public boolean writeToFile(String filename) {
        try(FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static City loadFromFile(String filename) {
        City city = null;
        try(FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            city = (City)ois.readObject();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return city;
    }

    public boolean addRoad(int x, int y) {
        int index = x * height + y;
        if (isInsideGrid(x, y) && grid[index] == null) {
            Road road = new Road(x, y);
            grid[index] = road;
            roads.add(road);
            return true;
        }
        return false;
    }

    public boolean addZone(int x, int y, ZoneType zoneType) {
        int index = x * height + y;
        if (isInsideGrid(x, y) && grid[index] == null) {
            Zone zone = new Zone(zoneType, x, y);
            grid[index] = zone;
            if (zones.containsKey(zoneType)) {
                zones.get(zoneType).add(zone);
            } else {
                zones.put(zoneType, new ArrayList<>(Collections.singletonList(zone)));
            }
            return true;
        }
        return false;
    }

    public boolean removeAt(int x, int y) {
        int index = x * height + y;
        if (isInsideGrid(x, y) && grid[index] != null) {
            if (grid[index] instanceof Road) {
                roads.remove(grid[index]);
            } else if (grid[index] instanceof Zone) {
                zones.get(((Zone)grid[index]).getZoneType()).remove(grid[index]);
            } else {
                return false;
            }
            grid[index] = null;
            return true;
        }
        return false;
    }

    public boolean isInsideGrid(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    public boolean isRoad(int x, int y) {
        return isInsideGrid(x, y) && grid[x * height + y] instanceof Road;
    }

    /**
     * Gets the roads adjacent to a point.
     *
     * @author Slimane
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return A set of coordinates of the adjacent roads
     */
    public Set<Pair<Integer, Integer>> getAdjacentRoads(int x, int y) {

        Set<Pair<Integer, Integer>> adjacentRoads = new HashSet<>();

        if (isRoad(x, y - 1))
            adjacentRoads.add(new Pair<>(x, y - 1));
        if (isRoad(x, y + 1))
            adjacentRoads.add(new Pair<>(x, y + 1));
        if (isRoad(x + 1, y))
            adjacentRoads.add(new Pair<>(x + 1, y));
        if (isRoad(x - 1, y))
            adjacentRoads.add(new Pair<>(x - 1, y));

        return adjacentRoads;
    }

    public void step() {
        moveVehicles();
        createTrips();
    }

    private void createTrips() {
        switch (getTimeOfDay()){
            // Very low traffic or no traffic ?
            case NIGHT:

                break;

            case DUSK:
                // R -> C
                // R -> I
                // R -> O
                break;

            case MORNING:
                // low traffic R -> R
                // low traffic R -> C

            case MIDDAY:
                // Medium traffic C -> C
                // Medium traffic I -> C
                // Medium traffic O -> C
                // Medium traffic C -> C
                // Medium traffic C -> I
                // Medium traffic C -> O
                break;

            case AFTERNOON:
                // low traffic R -> R
                // low traffic R -> C
                break;

            case DAWN:
                // C -> R
                // I -> R
                // O -> R
                break;
        }
        createHomeToShopTrip();
    }

    private void createHomeToShopTrip() { // faire for resid
        if (!zones.containsKey(ZoneType.RESIDENTIAL) || !zones.containsKey(ZoneType.COMMERCIAL)) return;
        Zone startingHome = zones
                .get(ZoneType.RESIDENTIAL)
                .get(MTRandom.getInstance().nextInt(zones.get(ZoneType.RESIDENTIAL).size()));
        Zone destinationShop = zones
                .get(ZoneType.COMMERCIAL)
                .get(MTRandom.getInstance().nextInt(zones.get(ZoneType.COMMERCIAL).size()));

        try {
            Pair<Integer, Integer> startingRoad = getAdjacentRoads(startingHome.getX(), startingHome.getY())
                    .stream()
                    .findFirst()
                    .get();

            Pair<Integer, Integer> destinationRoad = getAdjacentRoads(destinationShop.getX(), destinationShop.getY())
                    .stream()
                    .findFirst()
                    .get();

            Vehicle vehicle = new Vehicle();
            vehicle.createPath(this, startingRoad.getFirst(), startingRoad.getSecond(),
                    destinationRoad.getFirst(), destinationRoad.getSecond());

            ((Road)getEntityAt(startingRoad.getFirst(), startingRoad.getSecond())).addVehicle(vehicle);
            //System.out.println("[DEBUG] Created path");
        } catch (NoSuchElementException e) {
            System.err.println("[DEBUG] tried to create path but failed");
        }
    }

    private void moveVehicles() {
        List<Pair<Vehicle, Pair<Integer, Integer>>> changes = new ArrayList<>();

        for(Road road : roads) {
            road.updateState();
            Iterator<Vehicle> it = road.iterator();
            while (it.hasNext()) {
                Vehicle vehicle = it.next();
                it.remove();
                if (!vehicle.isAtDestination()) {
                    changes.add(new Pair<>(vehicle, vehicle.pollNextPosition()));
                    // handles buses
                }
            }
        }

        for (Pair<Vehicle, Pair<Integer, Integer>> p : changes) {
            Pair<Integer, Integer> nextPosition = p.getSecond();
            ((Road)grid[nextPosition.getFirst() * height + nextPosition.getSecond()]).addVehicle(p.getFirst());
        }
    }

    public List<Road> getRoads() {
        return roads;
    }
}
