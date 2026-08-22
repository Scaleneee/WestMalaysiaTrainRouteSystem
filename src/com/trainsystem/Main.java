package com.trainsystem;

import com.trainsystem.graph.TrainGraph;

import java.util.Scanner;
import java.util.Set;

import com.trainsystem.model.Station;
import com.trainsystem.ui.UIHelper;

public class Main {

    // declare a private TrainGraph use to store the stations and routes
    private static TrainGraph graph = new TrainGraph();

    // declare a scanner obj used to get user input
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // start the application
        mainMenu();
    }

    private static void mainMenu() {

        // ask user input
        int choice = 0;
        do {
            // display title
            UIHelper.printTitle("Train Route Management System");

            // display menu choices
            System.out.println("1. Manage Train Stations");
            System.out.println("2. Manage Train Routes");
            System.out.println("3. Search Train Routes");
            System.out.println("4. Display Train Network");
            System.out.println("0. Exit\n");

            // get user choice
            choice = UIHelper.getMenuChoice(scanner, 4);

            switch (choice) {
                case 1:
                    // manage train stations
                    stationMenu();
                    break;
                case 2:
                    // manage train routes

                    break;
                case 3:
                    // search train routes
                    break;
                case 4:
                    // display train network
                    break;
                default:
                    // choice = 0, exit
                    return;
            }
        } while (choice != 0);
    }

    private static void stationMenu() {

        // ask user input
        int choice = 0;
        do {
            // display title
            UIHelper.printTitle("Manage Train Stations");

            // display menu choices
            System.out.println("1. Add Train Station");
            System.out.println("2. Remove Train Station");
            System.out.println("3. Check Train Station");
            System.out.println("4. Display All Train Station");
            System.out.println("0. Back\n");

            choice = UIHelper.getMenuChoice(scanner, 4);

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

    private static void addTrainStationScreen() {
        // display title
        UIHelper.printTitle("Add Train Station");

        // ask user input the station code
        System.out.print("Enter Station Code: ");
        String stationCode = scanner.nextLine().trim().toUpperCase();

        // ask user input the station name
        System.out.print("Enter Station Name: ");
        String stationName = scanner.nextLine().trim().toUpperCase();

        // confirmation message
        boolean confirmation = UIHelper.getConfirmation(scanner, "\nAdd this station?");

        // create station variable
        Station station = new Station(stationCode, stationName);
        if (confirmation) {
            // add station confirmed
            if (graph.addVertex(station)) {
                // add successfully
                UIHelper.printSuccess("Station (" + station + ") added successfully.");
            } else {
                UIHelper.printError("Station (" + station + ") already exists.");
            }
        }
        // add station canceled
        // back to the previous screen
        UIHelper.pause(scanner);
    }

    private static void removeTrainStationScreen() {
        // display title
        UIHelper.printTitle("Remove Train Station");

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
            UIHelper.printError("Station not found.");
            UIHelper.pause(scanner);
            return;
        }

        // display warning message
        UIHelper.printWarning("Removing this station will also remove all routes connected to this station.\n");

        // confirmation
        boolean confirmation = UIHelper.getConfirmation(scanner, "Remove this station?");

        if (confirmation) {
            // remove confirmed
            if (graph.removeVertex(station)) {
                UIHelper.printSuccess("Station removed successfully.");
                UIHelper.pause(scanner);
            }
        }
    }

    private static void checkTrainStationScreen() {
        // display title
        UIHelper.printTitle("Check Train Station");

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
            UIHelper.pause(scanner);
        } else {
            UIHelper.printError("Station not found.");
            UIHelper.pause(scanner);
        }
    }

    private static void displayAllStationsScreen() {
        // display title
        UIHelper.printTitle("All Train Stations");

        // get all stations from the graph
        Set<Station> stations = graph.getVertices();

        // if no any station found in graph
        if (stations.isEmpty()) {
            UIHelper.printError("No train stations found.");
            return;
        }

        System.out.printf(
                "%-6s %-10s %-25s%n",
                "No.",
                "Code",
                "Station Name"
        );

        // divider
        UIHelper.printLine();

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
        UIHelper.printLine();

        // display total stations
        System.out.println("Total stations: " + graph.getSize());
        UIHelper.pause(scanner);
    }
}
