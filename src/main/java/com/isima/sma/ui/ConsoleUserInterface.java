package com.isima.sma.ui;

import com.isima.sma.city.City;
import com.isima.sma.entities.Road;
import com.isima.sma.entities.ZoneType;
import com.isima.sma.vehicles.Vehicle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUserInterface {

    private City city;

    public ConsoleUserInterface() {
        this.city = new City(10, 5);
    }

    public void start() {
        boolean keep = true;
        while (keep) {
            System.out.println(cityConsoleRepresentation());
            String userResponse = readConsole();
            if (userResponse == null || userResponse.equals("quit") || userResponse.equals("exit")) {
                keep = false;
            } else {
                try {
                    parseUserResponse(userResponse);
                } catch (Exception e) {
                    System.err.println("Command failed");
                    e.printStackTrace();
                }
            }
        }
    }

    private String cityConsoleRepresentation() {
        StringBuilder builder = new StringBuilder();
        for(int y = 0; y < city.getHeight(); ++y) {
            builder.append("[ ");
            for(int x = 0; x < city.getWidth(); ++x) {
                builder.append(city.getEntityAt(x, y) == null
                            ? "_" : city.getEntityAt(x, y).conRepresentation())
                       .append(' ');
            }
            builder.append("]\n");
        }
        return builder.toString();
    }

    private void parseUserResponse(String userResponse) {
        String[] params = userResponse.split(" ");

        switch(params[0]) {
            case "help":
                showHelp();
                break;
            case "set": // set 5 2 road
                int x = Integer.parseInt(params[1]);
                int y = Integer.parseInt(params[2]);
                if (params[3].equalsIgnoreCase("road")) {
                    city.addRoad(x, y);
                } else {
                    city.addZone(x, y, ZoneType.valueOf(params[3].toUpperCase()));
                }
                break;
            case "addvehicle": // Used for debug
                int xDep = Integer.parseInt(params[1]);
                int yDep = Integer.parseInt(params[2]);
                int xDest = Integer.parseInt(params[3]);
                int yDest = Integer.parseInt(params[4]);

                Vehicle vehicle = new Vehicle();
                vehicle.createPath(city, xDep, yDep, xDest, yDest);
                ((Road)city.getEntityAt(xDep, yDep)).addVehicle(vehicle);
                break;
            case "step":
                int steps = Integer.parseInt(params[1]);
                for(int i = 0; i < steps; ++i)
                    city.step();
                break;
            case "save":
                if (city.writeToFile(params[1])) {
                    System.out.println("Success: Saved city to "+ params[1]);
                } else {
                    System.out.println("Error: Could not save the city");
                }
                break;
            case "load":
                City newCity = null;
                if ((newCity = City.loadFromFile(params[1])) != null) {
                    System.out.println("Sucess: Loaded city from file");
                    city = newCity;
                } else {
                    System.out.println("Error: Could not load the city from " + params[1]);
                }
                break;
            default:
                System.err.println("Invalid command. Type help for available commands.");
        }
    }

    private void showHelp() {
        System.out.println("-- CitySim : Console UI --\n" +
                "set {x} {y} {type} - Add an element to the city (Road, Zone)\n" +
                "step {n} - Play out n steps of the simulation\n" +
                "quit - Quit the program\n");
    }

    private String readConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("> ");
        String userResponse = null;
        try {
            userResponse = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userResponse;
    }
}
