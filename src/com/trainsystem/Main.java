package com.trainsystem;

import com.trainsystem.data.TrainNetworkData;
import com.trainsystem.graph.TrainGraph;

import java.util.List;
import java.util.Scanner;

import com.trainsystem.model.Station;
import com.trainsystem.ui.screen.GraphDisplay;
import com.trainsystem.ui.screen.RouteMenu;
import com.trainsystem.ui.screen.SearchRouteMenu;
import com.trainsystem.ui.screen.StationMenu;
import com.trainsystem.ui.utils.ConsoleUtils;
import com.trainsystem.ui.utils.InputUtils;

public class Main {

    // declare a private TrainGraph use to store the stations and routes
    private static final TrainGraph GRAPH = new TrainGraph();

    // declare a scanner obj used to get user input
    private static final Scanner SCANNER = new Scanner(System.in);

    /*
     * initialize the dummy data when the class loaded
     */
    static {
        TrainNetworkData.initialize(GRAPH);
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
            ConsoleUtils.printTitle("Train Route Management System");

            // display menu choices
            System.out.println("1. Manage Train Stations");
            System.out.println("2. Manage Train Routes");
            System.out.println("3. Search Train Routes");
            System.out.println("4. Display Train Network");
            System.out.println("0. Exit\n");

            // get user choice
            choice = InputUtils.getMenuChoice(SCANNER, 4);

            switch (choice) {
                case 1:
                    // manage train stations
                    new StationMenu(SCANNER, GRAPH).start();
                    break;
                case 2:
                    // manage train routes
                    new RouteMenu(SCANNER, GRAPH).start();
                    break;
                case 3:
                    // search train routes
                    new SearchRouteMenu(SCANNER, GRAPH).start();
                    break;
                case 4:
                    // display train network
                    new GraphDisplay(SCANNER, GRAPH).start();
                    break;
                default:
                    // choice = 0, exit
                    return;
            }
        } while (choice != 0);
    }
}
