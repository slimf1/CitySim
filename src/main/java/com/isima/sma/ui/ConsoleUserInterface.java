package com.isima.sma.ui;

import com.isima.sma.city.City;
import com.isima.sma.entities.ZoneType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

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
        for(int i = 0; i < city.getHeight(); ++i) {
            builder.append("[ ");
            for(int j = 0; j < city.getWidth(); ++j) {
                builder.append(city.getEntityAt(i, j) == null ? "_" : city.getEntityAt(i, j).conRepresentation());
                builder.append(' ');
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
                int x = Integer.parseInt(params[2]);
                int y = Integer.parseInt(params[1]);
                if (params[3].equalsIgnoreCase("road")) {
                    city.addRoad(x, y);
                } else {
                    city.addZone(x, y, ZoneType.valueOf(params[3].toUpperCase()));
                }
                break;
            case "step":
                int steps = Integer.parseInt(params[1]);
                for(int i = 0; i < steps; ++i)
                    city.step();
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
