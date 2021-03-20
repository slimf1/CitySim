package com.isima.sma.ui;

import com.isima.sma.city.City;
import com.isima.sma.entities.ZoneType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUserInterface {

    private City city;

    public ConsoleUserInterface() {
        this.city = new City(5, 10);
        // prompt > set zone {x} {y} {type}, road
        // > step 5
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
        for(int i = 0; i < city.getWidth(); ++i) {
            builder.append("[ ");
            for(int j = 0; j < city.getHeight(); ++j) {
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
            case "set": // set 5 2 road
                int x = Integer.parseInt(params[2]);
                int y = Integer.parseInt(params[1]);
                if (params[3].equalsIgnoreCase("road")) {
                    city.addRoad(x, y);
                } else {
                    city.addZone(x, y, ZoneType.valueOf(params[3]));
                }
                break;
            case "step":
                break;
            default:
                System.err.println("Invalid command. Type help for available commands.");
        }
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
