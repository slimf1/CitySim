package com.isima.sma.city;

import com.isima.sma.entities.Entity;
import com.isima.sma.entities.Road;
import com.isima.sma.entities.Zone;
import com.isima.sma.entities.ZoneType;
import com.isima.sma.time.Clock;
import com.isima.sma.utils.MTRandom;
import com.isima.sma.utils.Pair;
import com.isima.sma.time.TimeOfDay;
import com.isima.sma.vehicles.Vehicle;

import java.io.*;
import java.util.*;

/**
 * Classe représentant une ville
 * @author Barthélemy J. et Slimane F.
 * @since 1.0
 */
public class City implements Serializable {

    /**
     * La longueur par défaut d'une ville si elle
     * n'a pas été spécifiée
     */
    private static final int DEFAULT_WIDTH = 15;
    /**
     * La hauteur par défaut d'une ville si elle
     * n'a pas été spécifiée
     */
    private static final int DEFAULT_HEIGHT = 10;
    /**
     * La probabilité d'apparition d'un trajet
     */
    private static final double SPAWN_PROBABILITY = 0.20;

    /**
     * La grille de la simulation, contenant les
     * éléments de la ville
     */
    private Entity[] grid;
    /**
     * Les routes de la ville
     */
    private List<Road> roads;
    /**
     * Les zones de la ville
     */
    private Map<ZoneType, List<Zone>> zones;
    /**
     * La longueur de la ville
     */
    private int width;
    /**
     * La hauteur de la ville
     */
    private int height;
    /**
     * La période actuelle de la simulation
     */
    private TimeOfDay timeOfDay;
    /**
     * Les différentes périodes disponibles au cours
     * de la simulation
     */
    private HashMap<TimeOfDay, Pair<Integer, Integer>> timeBins;

    /**
     * Constructeur par défaut d'une ville.
     * Fixe la hauteur et largeur par défaut
     */
    public City() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructeur d'une ville
     * @param width La largeur de la ville
     * @param height La hauteur de la ville
     */
    public City(int width, int height) {
        this.grid = new Entity[width * height];
        this.roads = new ArrayList<>();
        this.zones = new HashMap<>();
        this.width = width;
        this.height = height;
        this.timeBins = new HashMap<>();

        setupTimes();
        setTimeOfDay();
    }

    /**
     * Fixe les paramètres des différentes périodes d'une journée
     */
    private void setupTimes() {
        float part = Clock.TICK_MAX / 30;
        for(int i = 0; i < TimeOfDay.values().length; ++i) {

            int beginning = (int)(TimeOfDay.values()[i].getLower() * part + 1);
            int end = (int)(TimeOfDay.values()[i].getUpper() * part);
            timeBins.put(TimeOfDay.values()[i], new Pair<>(beginning, end));
        }
    }

    /**
     * Getter pour une entité de la ville
     * @param x L'abscisse de l'entité
     * @param y L'ordonnée de l'entité
     * @return L'entité au point spécifié, ou {@code null}
     * si les coordonnées sont en dehors de la grille ou s'il
     * n'y a pas d'entité au point spécifié
     */
    public Entity getEntityAt(int x, int y) {
        if (isInsideGrid(x, y))
            return grid[x * height + y];
        return null;
    }

    /**
     * @return La largeur de la ville
     */
    public final int getWidth() {
        return width;
    }

    /**
     * @return La hauteur de la ville
     */
    public final int getHeight() {
        return height;
    }

    /**
     * Fixe la période de la journée
     */
    public final void setTimeOfDay() {
        int currentTime = Clock.getInstance().getTime();
        TimeOfDay newTOD = null;
        if(this.timeOfDay == null || currentTime == timeBins.get(TimeOfDay.NIGHT).getFirst() ){
            this.timeOfDay = TimeOfDay.NIGHT;
        } else {
            if(currentTime < timeBins.get(TimeOfDay.NIGHT).getFirst() && currentTime > timeBins.get(timeOfDay).getSecond()){
                for(Map.Entry<TimeOfDay, Pair<Integer, Integer>> entry : timeBins.entrySet()){
                    if(currentTime >= entry.getValue().getFirst() && currentTime <= entry.getValue().getSecond()){
                        newTOD = entry.getKey();
                    }
                }
                this.timeOfDay = newTOD;
            }
        }
    }

    /**
     * @return La période actuelle de la simulation
     */
    public final TimeOfDay getTimeOfDay() {
        return this.timeOfDay;
    }

    /**
     * Sérialisation d'une ville dans un fichier
     * binaire
     * @param filename Le nom du fichier
     * @return {@code true} si la sérialisation a été
     * effectuée, {@code false} sinon
     */
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

    /**
     * Déserialisation d'une ville, lecture d'une instance
     * d'une ville à partir d'un fichier binaire valide
     * @param filename Le nom du fichier
     * @return La ville sauvegardée dans le fichier {@code filename}
     * ou {@code null} s'il y a eu une erreur dans la lecture
     */
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

    /**
     * Ajoute une route au point spécifié
     * @param x L'abscisse du point
     * @param y L'ordonnée du point
     * @return {@code true} si la route a été ajoutée,
     * {@code false} si un élément existe déjà au point
     * spécifié
     */
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

    /**
     * Ajoute une zone au point spécifié
     * @param x L'abscisse du point
     * @param y L'ordonnée du point
     * @return {@code true} si la zone a été ajoutée,
     * {@code false} si un élément existe déjà au point
     * spécifié
     */
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

    /**
     * Supprime un élément au point spécifié
     * @param x L'abscisse du point
     * @param y L'ordonnée du point
     * @return {@code true} si l'élément a été trouvé
     * et supprimé, {@code false} sinon
     */
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

    /**
     * Teste l'appartenance d'un point à la grille
     * @param x L'abscisse du point
     * @param y L'ordonnée du point
     * @return {@code true} si le point appartient à la
     * grille, {@code false} sinon
     */
    public boolean isInsideGrid(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    /**
     * Teste la présence d'une route à un point
     * donné
     * @param x L'abscisse du point
     * @param y L'ordonnée du point
     * @return {@code true} s'il y a une route au
     * point spécifié, {@code false} sinon
     */
    public boolean isRoad(int x, int y) {
        return isInsideGrid(x, y) && grid[x * height + y] instanceof Road;
    }

    /**
     * Retourne les routes adjacentes à un point
     *
     * @param x L'abscisse du point
     * @param y L'ordonnée du point
     * @return Un ensemble des routes adjacentes
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

    /**
     * Réalise une étape de la simulation
     */
    public void step() {
        moveVehicles();
        if(Clock.getInstance().getTime() % 2 == 0){
            createTrips();
        }
    }

    /**
     * Création des trajets en fonction
     * de l'heure
     */
    private void createTrips() {
        switch (getTimeOfDay()){
            case NIGHT:
                createWorkToHomeTrip(1);
                createHomeToHomeTrip(1);
                createHomeToWorkTrip(1);
                break;

            case DAWN:
                createHomeToShopTrip(6);
                createHomeToWorkTrip(6);
                break;

            case AFTERNOON:
                createHomeToHomeTrip(4);
                createHomeToShopTrip(4);
                break;

            case MORNING:
                createHomeToHomeTrip(3);
                createHomeToShopTrip(3);
                break;

            case MIDDAY:
                createHomeToShopTrip(3);
                createWorkToShopTrip(3);
                createShopToHomeTrip(3);
                createShopToWorkTrip(3);
                break;

            case DUSK:
                createShopToHomeTrip(5);
                createWorkToHomeTrip(4);
                break;

            default:
                // Nothing
        }
    }

    /**
     * Création d'un trajet entre deux zones de la ville
     * @param start Le type de zone du point de départ
     * @param destination Le type de zone du point d'arrivée
     * @param n Le nombre de trajets maximum à créer
     */
    private void createTrip(ZoneType start, ZoneType destination, int n){
        for (Entity ent : zones.get(start)){
            if (!zones.containsKey(start) || !zones.containsKey(destination)) return;
            Zone startingHome = zones
                    .get(start)
                    .get(MTRandom.getInstance().nextInt(zones.get(start).size()));
            Zone destinationShop = zones
                    .get(destination)
                    .get(MTRandom.getInstance().nextInt(zones.get(destination).size()));


            for(int i = 0; i < n; i++){
                if(MTRandom.getInstance().nextDouble() < SPAWN_PROBABILITY){
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
                    } catch (NoSuchElementException e) {
                        System.err.println("[DEBUG] tried to create path but failed");
                    }
                }

            }
        }
    }

    /**
     * Créé au plus {@code n} trajets d'une zone résidentielle
     * vers une zone commerciale
     */
    private void createHomeToShopTrip(int n) {
        createTrip(ZoneType.RESIDENTIAL, ZoneType.COMMERCIAL, n);
    }

    /**
     * Créé au plus {@code n} trajets d'une zone commerciale
     * vers une zone résidentielle
     */
    private void createShopToHomeTrip(int n) {
        createTrip(ZoneType.COMMERCIAL, ZoneType.RESIDENTIAL, n);
    }

    /**
     * Créé au plus {@code n} trajets d'une zone résidentielle
     * vers une zone commerciale ou industrielle
     */
    private void createHomeToWorkTrip(int n) {
        createTrip(ZoneType.RESIDENTIAL, ZoneType.INDUSTRIAL, n);
        createTrip(ZoneType.RESIDENTIAL, ZoneType.OFFICE, n);
    }

    /**
     * Créé au plus {@code n} trajets d'une zone commerciale et
     * industrielle vers une zone résdientielle
     */
    private void createWorkToHomeTrip(int n) {
        createTrip(ZoneType.OFFICE, ZoneType.RESIDENTIAL, n);
        createTrip(ZoneType.INDUSTRIAL, ZoneType.RESIDENTIAL, n);
    }

    /**
     * Créé au plus {@code n} trajets d'une zone résidentielle
     * vers une autre zone résidentielle
     */
    private void createHomeToHomeTrip(int n) {
        createTrip(ZoneType.RESIDENTIAL, ZoneType.RESIDENTIAL, n);
    }

    /**
     * Créé au plus {@code n} trajets d'une zone de bureaux ou industrielle
     * vers une zone commerciale
     */
    private void createWorkToShopTrip(int n) {
        createTrip(ZoneType.OFFICE, ZoneType.COMMERCIAL, n);
        createTrip(ZoneType.INDUSTRIAL, ZoneType.COMMERCIAL, n);
    }

    /**
     * Créé au plus {@code n} trajets d'une zone commerciale
     * vers une zone industrielle ou de bureaux
     */
    private void createShopToWorkTrip(int n) {
        createTrip(ZoneType.COMMERCIAL, ZoneType.INDUSTRIAL, n);
        createTrip(ZoneType.COMMERCIAL, ZoneType.OFFICE, n);

    }

    /**
     * Déplace les instances des véhicules en fonction
     * de leur trajets sur le réseau de routes de la ville
     */
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
                }
            }
        }

        for (Pair<Vehicle, Pair<Integer, Integer>> p : changes) {
            Pair<Integer, Integer> nextPosition = p.getSecond();
            ((Road)grid[nextPosition.getFirst() * height + nextPosition.getSecond()]).addVehicle(p.getFirst());
        }
    }

    /**
     * Getter des routes de la ville
     * @return Les routes de la ville
     */
    public List<Road> getRoads() {
        return roads;
    }
}
