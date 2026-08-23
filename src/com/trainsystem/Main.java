package com.trainsystem;

import com.trainsystem.graph.TrainGraph;

import java.util.Scanner;
import java.util.Set;

import com.trainsystem.model.Station;
import com.trainsystem.ui.UIHelper;

public class Main {

    // declare a private TrainGraph use to store the stations and routes
    private static final TrainGraph GRAPH = new TrainGraph();

    // declare a scanner obj used to get user input
    private static final Scanner SCANNER = new Scanner(System.in);

    static {
        // declare all vertices (Station)
        Station kkb = new Station("KKB", "");
        Station kls = new Station("KLS", "KL Sentral");
        Station bpr = new Station("BPR", "");
        Station pad = new Station("PAD", "");
        Station ara = new Station("ARA", "");
        Station srm = new Station("SRM", "");
        Station als = new Station("ALS", "Alor Setar");
        Station gms = new Station("GMS", "Gemas");
        Station seg = new Station("SEG", "Segamat");
        Station klv = new Station("KLV", "Kluang");
        Station jhb = new Station("JHB", "Johor Bahru");
        Station spg = new Station("SPG", "");
        Station but = new Station("BUT", "Butterworth");
        Station tas = new Station("TAS", "Taiping");
        Station bm  = new Station("BM", "Bukit Mertajam");
        Station iph = new Station("IPH", "Ipoh");
        Station tgs = new Station("TGS", "");


    }

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
            choice = UIHelper.getMenuChoice(SCANNER, 4);

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

            choice = UIHelper.getMenuChoice(SCANNER, 4);

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
        String stationCode = SCANNER.nextLine().trim().toUpperCase();

        // ask user input the station name
        System.out.print("Enter Station Name: ");
        String stationName = SCANNER.nextLine().trim().toUpperCase();

        // confirmation message
        boolean confirmation = UIHelper.getConfirmation(SCANNER, "\nAdd this station?");

        // create station variable
        Station station = new Station(stationCode, stationName);
        if (confirmation) {
            // add station confirmed
            if (GRAPH.addVertex(station)) {
                // add successfully
                UIHelper.printSuccess("Station (" + station + ") added successfully.");
            } else {
                UIHelper.printError("Station (" + station + ") already exists.");
            }
        }
        // add station canceled
        // back to the previous screen
        UIHelper.pause(SCANNER);
    }

    private static void removeTrainStationScreen() {
        // display title
        UIHelper.printTitle("Remove Train Station");

        // ask user input the station code
        System.out.print("Enter Station Code: ");
        String stationCode = SCANNER.nextLine().trim().toUpperCase();

        // search station, to know whether exists or not
        Station station = GRAPH.searchStation(stationCode);

        // display station message
        if (station != null) {
            System.out.println("Station Found:");
            System.out.println("Code: " + station.getStationCode());
            System.out.println("Name: " + station.getStationName());
        } else {
            UIHelper.printError("Station not found.");
            UIHelper.pause(SCANNER);
            return;
        }

        // display warning message
        UIHelper.printWarning("Removing this station will also remove all routes connected to this station.\n");

        // confirmation
        boolean confirmation = UIHelper.getConfirmation(SCANNER, "Remove this station?");

        if (confirmation) {
            // remove confirmed
            if (GRAPH.removeVertex(station)) {
                UIHelper.printSuccess("Station removed successfully.");
                UIHelper.pause(SCANNER);
            }
        }
    }

    private static void checkTrainStationScreen() {
        // display title
        UIHelper.printTitle("Check Train Station");

        // ask user input the station code
        System.out.print("Enter Station Code: ");
        String stationCode = SCANNER.nextLine().trim().toUpperCase();

        // search station, to know whether exists or not
        Station station = GRAPH.searchStation(stationCode);

        // display station message
        if (station != null) {
            System.out.println("Station Found:");
            System.out.println("Code: " + station.getStationCode());
            System.out.println("Name: " + station.getStationName());
            UIHelper.pause(SCANNER);
        } else {
            UIHelper.printError("Station not found.");
            UIHelper.pause(SCANNER);
        }
    }

    private static void displayAllStationsScreen() {
        // display title
        UIHelper.printTitle("All Train Stations");

        // get all stations from the graph
        Set<Station> stations = GRAPH.getVertices();

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
        System.out.println("Total stations: " + GRAPH.getSize());
        UIHelper.pause(SCANNER);
    }
}
