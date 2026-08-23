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
        Station kkb = new Station("KKB", "Kuala Kubu Bharu");
        Station kls = new Station("KLS", "KL Sentral");
        Station bdr = new Station("BDR", "Bandar Tasik Selatan");
        Station pad = new Station("PAD", "Padang Besar");
        Station ara = new Station("ARA", "Arau");
        Station srm = new Station("SRM", "Seremban");
        Station als = new Station("ALS", "Alor Setar");
        Station gms = new Station("GMS", "Gemas");
        Station seg = new Station("SEG", "Segamat");
        Station klv = new Station("KLV", "Kluang");
        Station jhb = new Station("JHB", "Johor Bahru");
        Station spg = new Station("SPG", "Sungai Petani");
        Station but = new Station("BUT", "Butterworth");
        Station tas = new Station("TAS", "Taiping");
        Station bm  = new Station("BM", "Bukit Mertajam");
        Station iph = new Station("IPH", "Ipoh");
        Station tgs = new Station("TGS", "Tasek Gelugor");

        //add all the vertices (Station) into graph
        GRAPH.addVertex(kkb);
        GRAPH.addVertex(kls);
        GRAPH.addVertex(bdr);
        GRAPH.addVertex(pad);
        GRAPH.addVertex(ara);
        GRAPH.addVertex(srm);
        GRAPH.addVertex(als);
        GRAPH.addVertex(gms);
        GRAPH.addVertex(seg);
        GRAPH.addVertex(klv);
        GRAPH.addVertex(jhb);
        GRAPH.addVertex(spg);
        GRAPH.addVertex(but);
        GRAPH.addVertex(tas);
        GRAPH.addVertex(bm);
        GRAPH.addVertex(iph);
        GRAPH.addVertex(tgs);

        //add all the edges (Route) into graph
        GRAPH.addEdge(pad, ara, 22, 5.00);
        GRAPH.addEdge(ara, pad, 24, 5.00);
        GRAPH.addEdge(ara, als, 18, 4.00);
        GRAPH.addEdge(als, ara, 20, 4.00);
        GRAPH.addEdge(als, spg, 40, 8.00);
        GRAPH.addEdge(spg, als, 42, 8.00);
        GRAPH.addEdge(spg, tgs, 28, 6.00);
        GRAPH.addEdge(tgs, spg, 30, 6.00);
        GRAPH.addEdge(tgs, bm, 20, 5.00);
        GRAPH.addEdge(bm, tgs, 22, 5.00);
        GRAPH.addEdge(bm, but, 15, 4.00);
        GRAPH.addEdge(but, bm, 17, 4.00);
        GRAPH.addEdge(bm, bdr, 30, 7.00);
        GRAPH.addEdge(bdr, bm, 32, 7.00);
        GRAPH.addEdge(bdr, tas, 35, 8.00);
        GRAPH.addEdge(tas, bdr, 38, 8.00);
        GRAPH.addEdge(tas, iph, 55, 12.00);
        GRAPH.addEdge(iph, tas, 58, 12.00);
        GRAPH.addEdge(iph, kkb, 95, 20.00);
        GRAPH.addEdge(kkb, iph, 100, 20.00);
        GRAPH.addEdge(kkb, kls, 60, 14.00);
        GRAPH.addEdge(kls, kkb, 65, 14.00);
        GRAPH.addEdge(kls, srm, 70, 13.00);
        GRAPH.addEdge(srm, kls, 72, 13.00);
        GRAPH.addEdge(srm, gms, 105, 19.00);
        GRAPH.addEdge(gms, srm, 110, 19.00);
        GRAPH.addEdge(gms, seg, 65, 13.00);
        GRAPH.addEdge(seg, gms, 68, 13.00);
        GRAPH.addEdge(seg, klv, 80, 15.00);
        GRAPH.addEdge(klv, seg, 85, 15.00);
        GRAPH.addEdge(klv, jhb, 95, 18.00);
        GRAPH.addEdge(jhb, klv, 100, 18.00);
        GRAPH.addEdge(pad, als, 35, 8.00);
        GRAPH.addEdge(als, pad, 38, 8.00);
        GRAPH.addEdge(pad, tgs, 100, 19.00);
        GRAPH.addEdge(tgs, pad, 105, 19.00);
        GRAPH.addEdge(ara, spg, 55, 11.00);
        GRAPH.addEdge(spg, ara, 58, 11.00);
        GRAPH.addEdge(als, tgs, 60, 12.00);
        GRAPH.addEdge(tgs, als, 65, 12.00);
        GRAPH.addEdge(spg, bm, 42, 9.00);
        GRAPH.addEdge(bm, spg, 45, 9.00);
        GRAPH.addEdge(spg, but, 55, 11.00);
        GRAPH.addEdge(but, spg, 58, 11.00);
        GRAPH.addEdge(tgs, bdr, 65, 13.00);
        GRAPH.addEdge(bdr, tgs, 68, 13.00);
        GRAPH.addEdge(but, bdr, 50, 10.00);
        GRAPH.addEdge(bdr, but, 52, 10.00);
        GRAPH.addEdge(but, iph, 125, 24.00);
        GRAPH.addEdge(iph, but, 130, 24.00);
        GRAPH.addEdge(bdr, iph, 80, 17.00);
        GRAPH.addEdge(iph, bdr, 85, 17.00);
        GRAPH.addEdge(bm, tas, 75, 15.00);
        GRAPH.addEdge(tas, bm, 78, 15.00);
        GRAPH.addEdge(iph, kls, 145, 30.00);
        GRAPH.addEdge(kls, iph, 150, 30.00);
        GRAPH.addEdge(kkb, srm, 125, 24.00);
        GRAPH.addEdge(srm, kkb, 130, 24.00);
        GRAPH.addEdge(kls, gms, 155, 29.00);
        GRAPH.addEdge(gms, kls, 160, 29.00);
        GRAPH.addEdge(srm, seg, 155, 28.00);
        GRAPH.addEdge(seg, srm, 160, 28.00);
        GRAPH.addEdge(gms, klv, 125, 24.00);
        GRAPH.addEdge(klv, gms, 130, 24.00);
        GRAPH.addEdge(seg, jhb, 165, 25.00);
        GRAPH.addEdge(jhb, seg, 170, 25.00);
        GRAPH.addEdge(pad, kls, 330, 65.00);
        GRAPH.addEdge(kls, pad, 340, 65.00);
        GRAPH.addEdge(ara, kls, 310, 60.00);
        GRAPH.addEdge(kls, ara, 320, 60.00);
        GRAPH.addEdge(als, kls, 285, 55.00);
        GRAPH.addEdge(kls, als, 295, 55.00);
        GRAPH.addEdge(spg, kls, 260, 51.00);
        GRAPH.addEdge(kls, spg, 270, 51.00);
        GRAPH.addEdge(but, kls, 245, 49.00);
        GRAPH.addEdge(kls, but, 250, 49.00);
        GRAPH.addEdge(tas, kls, 185, 37.00);
        GRAPH.addEdge(kls, tas, 190, 37.00);
        GRAPH.addEdge(iph, gms, 300, 52.00);
        GRAPH.addEdge(gms, iph, 310, 52.00);
        GRAPH.addEdge(iph, jhb, 430, 76.00);
        GRAPH.addEdge(jhb, iph, 440, 76.00);
        GRAPH.addEdge(kls, seg, 220, 44.00);
        GRAPH.addEdge(seg, kls, 230, 44.00);
        GRAPH.addEdge(kls, klv, 265, 55.00);
        GRAPH.addEdge(klv, kls, 275, 55.00);
        GRAPH.addEdge(kls, jhb, 300, 72.00);
        GRAPH.addEdge(jhb, kls, 310, 72.00);
        GRAPH.addEdge(srm, klv, 230, 42.00);
        GRAPH.addEdge(klv, srm, 240, 42.00);
        GRAPH.addEdge(gms, jhb, 220, 40.00);
        GRAPH.addEdge(jhb, gms, 225, 40.00);
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
