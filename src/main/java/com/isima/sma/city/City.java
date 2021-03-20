package com.isima.sma.city;

import com.isima.sma.entities.Entity;
import com.isima.sma.entities.Road;
import com.isima.sma.entities.Zone;
import com.isima.sma.entities.ZoneType;
import com.isima.sma.utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class City {

    private static final int DEFAULT_WIDTH = 5;
    private static final int DEFAULT_HEIGHT = 10;

    private Entity[] grid;
    private List<Road> roads;
    private List<Zone> zones; // Map : zoneType => List ?
    private int width;
    private int height;

    public City() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public City(int width, int height) {
        this.grid = new Entity[width * height];
        this.roads = new ArrayList<>();
        this.zones = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public Entity getEntityAt(int x, int y) {
        if (isInsideGrid(x, y))
            return grid[x * width + y];
        return null;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public boolean addRoad(int x, int y) {
        int index = x * width + y;
        if (isInsideGrid(x, y) && grid[index] == null) {
            Road road = new Road();
            grid[index] = road;
            roads.add(road);
            return true;
        }
        return false;
    }

    public boolean addZone(int x, int y, ZoneType zoneType) {
        int index = x * width + y;
        if (isInsideGrid(x, y) && grid[index] == null) {
            Zone zone = new Zone(zoneType);
            grid[index] = zone;
            zones.add(zone);
            return true;
        }
        return false;
    }

    public boolean isInsideGrid(int x, int y) {
        return (x >= 0 && x < height) && (y >= 0 && y < width);
    }

    public boolean isRoad(int x, int y) {
        return isInsideGrid(x, y) && grid[x * width + y] != null;
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
        if (!isRoad(x, y))
            throw new IllegalArgumentException("");

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

    }
}
