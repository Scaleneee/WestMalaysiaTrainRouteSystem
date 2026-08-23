package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainGraph;
import com.trainsystem.model.Station;
import com.trainsystem.ui.utils.ConsoleUtils;
import com.trainsystem.ui.utils.InputUtils;

import java.util.Scanner;
import java.util.Set;

public class StationMenu {

    // scanner and graph obj passed by main
    private final Scanner scanner;
    private final TrainGraph graph;

    public StationMenu(Scanner scanner, TrainGraph graph) {
        this.scanner = scanner;
        this.graph = graph;
    }

    /**
     * main station menu entrance
     */
    public void start() {

        // ask user input
        int choice = 0;
        do {
            // display title
            ConsoleUtils.printTitle("Manage Train Stations");

            // display menu choices
            System.out.println("1. Add Train Station");
            System.out.println("2. Remove Train Station");
            System.out.println("3. Check Train Station");
            System.out.println("4. Display All Train Station");
            System.out.println("0. Back\n");

            choice = InputUtils.getMenuChoice(scanner, 4);

            switch (choice) {
                case 1:
                    // add train station
                    addTrainStationScreen();
                    break;
                case 2:
                    // remove train station
                    removeTrainStationScreen();
                    break;
                case 3:
                    // check train station
                    checkTrainStationScreen();
                    break;
                case 4:
                    // display all trian station
                    displayAllStationsScreen();
                    break;
                default:
                    // choice = 0, exit
                    return;
            }
        } while (choice != 0);
    }

    private void addTrainStationScreen() {
        // display title
        ConsoleUtils.printTitle("Add Train Station");

        // ask user input the station code
        System.out.print("Enter Station Code: ");
        String stationCode = scanner.nextLine().trim().toUpperCase();

        // ask user input the station name
        System.out.print("Enter Station Name: ");
        String stationName = scanner.nextLine().trim().toUpperCase();

        // confirmation message
        boolean confirmation = InputUtils.getConfirmation(scanner, "\nAdd this station?");

        // create station variable
        Station station = new Station(stationCode, stationName);
        if (confirmation) {
            // add station confirmed
            if (graph.addVertex(station)) {
                // add successfully
                ConsoleUtils.printSuccess("Station (" + station + ") added successfully.");
            } else {
                ConsoleUtils.printError("Station (" + station + ") already exists.");
            }
        }
        // add station canceled
        // back to the previous screen
        ConsoleUtils.pause(scanner);
    }

    private void removeTrainStationScreen() {
        // display title
        ConsoleUtils.printTitle("Remove Train Station");

        // ask user input the station code
        System.out.print("Enter Station Code: ");
        String stationCode = scanner.nextLine().trim().toUpperCase();

        // search station, to know whether exists or not
        Station station = graph.searchStation(stationCode);

        // display station message
        if (station != null) {
            System.out.println("Station Found:");
            System.out.println("Code: " + station.getStationCode());
            System.out.println("Name: " + station.getStationName());
        } else {
            ConsoleUtils.printError("Station not found.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // display warning message
        ConsoleUtils.printWarning("Removing this station will also remove all routes connected to this station.\n");

        // confirmation
        boolean confirmation = InputUtils.getConfirmation(scanner, "Remove this station?");

        if (confirmation) {
            // remove confirmed
            if (graph.removeVertex(station)) {
                ConsoleUtils.printSuccess("Station removed successfully.");
                ConsoleUtils.pause(scanner);
            }
        }
    }

    private void checkTrainStationScreen() {
        // display title
        ConsoleUtils.printTitle("Check Train Station");

        // ask user input the station code
        System.out.print("Enter Station Code: ");
        String stationCode = scanner.nextLine().trim().toUpperCase();

        // search station, to know whether exists or not
        Station station = graph.searchStation(stationCode);

        // display station message
        if (station != null) {
            System.out.println("Station Found:");
            System.out.println("Code: " + station.getStationCode());
            System.out.println("Name: " + station.getStationName());
            ConsoleUtils.pause(scanner);
        } else {
            ConsoleUtils.printError("Station not found.");
            ConsoleUtils.pause(scanner);
        }
    }

    private void displayAllStationsScreen() {
        // display title
        ConsoleUtils.printTitle("All Train Stations");

        // get all stations from the graph
        Set<Station> stations = graph.getVertices();

        // if no any station found in graph
        if (stations.isEmpty()) {
            ConsoleUtils.printError("No train stations found.");
            return;
        }

        System.out.printf(
                "%-6s %-10s %-25s%n",
                "No.",
                "Code",
                "Station Name"
        );

        // divider
        ConsoleUtils.printLine();

        // display stations
        int number = 1;
        for (Station station : stations) {
            System.out.printf(
                    "%-6d %-10s %-25s%n",
                    number,
                    station.getStationCode(),
                    station.getStationName()
            );

            number++;
        }
        ConsoleUtils.printLine();

        // display total stations
        System.out.println("Total stations: " + graph.getSize());
        ConsoleUtils.pause(scanner);
    }
}
