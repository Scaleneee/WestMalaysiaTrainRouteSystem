package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainEdge;
import com.trainsystem.graph.TrainGraph;
import com.trainsystem.model.Station;
import com.trainsystem.ui.UIHelper;

import java.util.List;
import java.util.Scanner;

public class RouteMenu {

    // scanner and graph passed by main
    private final Scanner scanner;
    private final TrainGraph graph;

    public RouteMenu(Scanner scanner, TrainGraph graph) {
        this.scanner = scanner;
        this.graph = graph;
    }

    /**
     * main route menu entrance
     */
    public void start() {

        // user choice
        int choice = 0;

        do {
            // display title
            UIHelper.printTitle("Manage Train Routes");

            // display menu choices
            System.out.println("1. Add Train Route");
            System.out.println("2. Remove Train Route");
            System.out.println("3. Check Direct Train Route");
            System.out.println("4. Display All Train Routes");
            System.out.println("0. Back\n");

            choice = UIHelper.getMenuChoice(scanner, 4);

            switch (choice) {
                case 1:
                    // add train route
                    addTrainRouteScreen();
                    break;
                case 2:
                    // remove train route
                    removeTrainRouteScreen();
                    break;
                case 3:
                    // check direct train route
                    checkDirectTrainRouteScreen();
                    break;
                case 4:
                    // display all trian routes
                    displayAllTrainRoutesScreen();
                    break;
                default:
                    // choice = 0, exit
                    return;
            }
        } while (choice != 0);
    }

    private void addTrainRouteScreen() {
        // display title
        UIHelper.printTitle("Add Train Route");

        // ask user input source station code
        System.out.print("Enter Source Station Code: ");
        String sourceCode = scanner.nextLine().trim().toUpperCase();

        // ask user input destination station code
        System.out.print("Enter Destination Station Code: ");
        String destinationCode = scanner.nextLine().trim().toUpperCase();

        // travel duration in minutes
        int duration = UIHelper.getPositiveInteger(scanner, "Enter Travel Duration (minutes): ");

        // travel duration in minutes
        double ticketFare = UIHelper.getPositiveDouble(scanner, "Enter Ticket Fare (RM): ");

        // display route
        System.out.println("\nRoute:");
        System.out.println(sourceCode + " -> " + destinationCode);
        System.out.println("Duration: " + duration + " minutes");
        System.out.printf("Ticket Fare: RM %.2f%n", ticketFare);

        // get source station and destination station based on the station code
        Station source = graph.getStationByCode(sourceCode);
        Station destination = graph.getStationByCode(destinationCode);

        // confirmation
        boolean confirmation = UIHelper.getConfirmation(scanner, "\nAdd this route?");
        if (confirmation) {
            // confirm add
            if (source == null || destination == null) {
                // source or destination not found
                UIHelper.printError("Source or destination station not found.");
                UIHelper.pause(scanner);
                return;
            }

            if (graph.addEdge(source, destination, duration, ticketFare)) {
                // add successfully
                UIHelper.printSuccess("Train route (" + sourceCode + " -> " + destinationCode + ") added successfully.");
                UIHelper.pause(scanner);
            } else {
                // route has already exists
                UIHelper.printError("Train route (" + sourceCode + " -> " + destinationCode + ") already exists.");
                UIHelper.pause(scanner);
            }
        }
    }

    private void removeTrainRouteScreen() {
        // display title
        UIHelper.printTitle("Remove Train Route");

        // ask user input source station code
        System.out.print("Enter Source Station Code: ");
        String sourceCode = scanner.nextLine().trim().toUpperCase();

        // ask user input destination station code
        System.out.print("Enter Destination Station Code: ");
        String destinationCode = scanner.nextLine().trim().toUpperCase();

        // get station obj by using the station code
        Station source = graph.getStationByCode(sourceCode);
        Station destination = graph.getStationByCode(destinationCode);

        if (source == null || destination == null) {
            // source or destination station not found
            // source or destination not found
            UIHelper.printError("Source or destination station not found.");
            UIHelper.pause(scanner);
            return;
        }

        // get the edge obj
        TrainEdge<Station> route = graph.getEdge(source, destination);

        if (route == null) {
            UIHelper.printError("Tain route not found...");
            UIHelper.pause(scanner);
            return;
        }

        // display route information
        System.out.println("\nRoute Found: ");
        System.out.println(sourceCode + " -> " + destinationCode);
        System.out.println("Duration: " + route.getDuration() + " minutes");
        System.out.printf("Ticket Fare: RM %.2f%n", route.getPrice());

        // confirmation
        boolean confirmation = UIHelper.getConfirmation(scanner, "\nRemove this route?");
        if (confirmation) {
            // remove confirmed
            if (graph.removeEdge(source, destination)) {
                // successful
                UIHelper.printSuccess("Train route (" + sourceCode + " -> " + destinationCode + ") removed successfully.");
                UIHelper.pause(scanner);
            }
        }
    }

    private void checkDirectTrainRouteScreen() {
        // title
        UIHelper.printTitle("Check Direct Train Route");

        // ask user input source station code
        System.out.print("Enter Source Station Code: ");
        String sourceCode = scanner.nextLine().trim().toUpperCase();

        // ask user input destination station code
        System.out.print("Enter Destination Station Code: ");
        String destinationCode = scanner.nextLine().trim().toUpperCase();

        // get station obj by using the station code
        Station source = graph.getStationByCode(sourceCode);
        Station destination = graph.getStationByCode(destinationCode);

        if (source == null || destination == null) {
            // source or destination station not found
            // source or destination not found
            UIHelper.printError("Source or destination station not found.");
            UIHelper.pause(scanner);
            return;
        }

        // get the edge obj
        TrainEdge<Station> route = graph.getEdge(source, destination);

        if (route == null) {
            UIHelper.printError("Tain route not found...");
            UIHelper.pause(scanner);
            return;
        }

        // display route information
        System.out.println("\nRoute Found: ");
        System.out.println(sourceCode + " -> " + destinationCode);
        System.out.println("Duration: " + route.getDuration() + " minutes");
        System.out.printf("Ticket Fare: RM %.2f%n", route.getPrice());

        UIHelper.pause(scanner);
    }

    private void displayAllTrainRoutesScreen() {
        // title
        UIHelper.printTitle("Display All Train Routes");

        int number = 1;
        // print table header
        System.out.printf(
                "%-5s %-8s %-8s %-12s %-13s%n",
                "No.",
                "From",
                "To",
                "Duration",
                "Ticket Fare"
        );

        // divider
        UIHelper.printLine();

        for (Station source : graph.getVertices()) {
            // get all edges from the source station
            List<TrainEdge<Station>> routes = graph.getEdges(source);

            for (TrainEdge<Station> route : routes) {
                // display route information
                System.out.printf(
                        "%-5d %-8s %-8s %3d min      RM %7.2f%n",
                        number++,
                        source.getStationCode(),
                        route.getDestination().getStationCode(),
                        route.getDuration(),
                        route.getPrice()
                );
            }
        }
        // divider
        UIHelper.printLine();
        System.out.println("Total Routes: " + (number - 1));
        UIHelper.pause(scanner);
    }
}
