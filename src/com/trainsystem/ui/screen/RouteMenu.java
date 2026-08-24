package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainEdge;
import com.trainsystem.graph.TrainGraph;
import com.trainsystem.model.Station;
import com.trainsystem.ui.utils.ConsoleUtils;
import com.trainsystem.ui.utils.InputUtils;

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
            ConsoleUtils.printTitle("Manage Train Routes");

            // display menu choices
            System.out.println("1. Add Train Route");
            System.out.println("2. Remove Train Route");
            System.out.println("3. Check Direct Train Route");
            System.out.println("4. Display All Train Routes");
            System.out.println("0. Back\n");

            choice = InputUtils.getMenuChoice(scanner, 4);

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
        ConsoleUtils.printTitle("Add Train Route");

        // ask user input source station code
        System.out.print("Enter Source Station Code: ");
        String sourceCode = scanner.nextLine().trim().toUpperCase();

        // ask user input destination station code
        System.out.print("Enter Destination Station Code: ");
        String destinationCode = scanner.nextLine().trim().toUpperCase();

        // travel duration in minutes
        int duration = InputUtils.getPositiveInteger(scanner, "Enter Travel Duration (minutes): ");

        // travel duration in minutes
        double ticketFare = InputUtils.getPositiveDouble(scanner, "Enter Ticket Fare (RM): ");

        // display route
        System.out.println("\nRoute:");
        System.out.println(sourceCode + " -> " + destinationCode);
        System.out.println("Duration: " + ConsoleUtils.formatDuration(duration));
        System.out.printf("Ticket Fare: RM %.2f%n", ticketFare);

        // get source station and destination station based on the station code
        Station source = graph.getStationByCode(sourceCode);
        Station destination = graph.getStationByCode(destinationCode);

        // confirmation
        boolean confirmation = InputUtils.getConfirmation(scanner, "\nAdd this route?");
        if (confirmation) {
            // confirm add
            if (source == null || destination == null) {
                // source or destination not found
                ConsoleUtils.printError("Source or destination station not found.");
                ConsoleUtils.pause(scanner);
                return;
            }

            if (graph.addEdge(source, destination, duration, ticketFare)) {
                // add successfully
                ConsoleUtils.printSuccess("Train route (" + sourceCode + " -> " + destinationCode + ") added successfully.");
                ConsoleUtils.pause(scanner);
            } else {
                // route has already exists
                ConsoleUtils.printError("Train route (" + sourceCode + " -> " + destinationCode + ") already exists.");
                ConsoleUtils.pause(scanner);
            }
        }
    }

    private void removeTrainRouteScreen() {
        // display title
        ConsoleUtils.printTitle("Remove Train Route");

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
            ConsoleUtils.printError("Source or destination station not found.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // get the edge obj
        TrainEdge<Station> route = graph.getEdge(source, destination);

        if (route == null) {
            ConsoleUtils.printError("Tain route not found.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // display route information
        System.out.println("\nRoute Found: ");
        System.out.println(sourceCode + " -> " + destinationCode);
        System.out.println("Duration: " + ConsoleUtils.formatDuration(route.getDuration()));
        System.out.printf("Ticket Fare: RM %.2f%n", route.getPrice());

        // confirmation
        boolean confirmation = InputUtils.getConfirmation(scanner, "\nRemove this route?");
        if (confirmation) {
            // remove confirmed
            if (graph.removeEdge(source, destination)) {
                // successful
                ConsoleUtils.printSuccess("Train route (" + sourceCode + " -> " + destinationCode + ") removed successfully.");
                ConsoleUtils.pause(scanner);
            }
        }
    }

    private void checkDirectTrainRouteScreen() {
        // title
        ConsoleUtils.printTitle("Check Direct Train Route");

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
            ConsoleUtils.printError("Source or destination station not found.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // get the edge obj
        TrainEdge<Station> route = graph.getEdge(source, destination);

        if (route == null) {
            ConsoleUtils.printError("Tain route not found.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // display route information
        System.out.println("\nRoute Found: ");
        System.out.println(sourceCode + " -> " + destinationCode);
        System.out.println("Duration: " + ConsoleUtils.formatDuration(route.getDuration()));
        System.out.printf("Ticket Fare: RM %.2f%n", route.getPrice());

        ConsoleUtils.pause(scanner);
    }

    private void displayAllTrainRoutesScreen() {
        // title
        ConsoleUtils.printTitle("Display All Train Routes");

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
        ConsoleUtils.printLine();

        for (Station source : graph.getVertices()) {
            // get all edges from the source station
            List<TrainEdge<Station>> routes = graph.getEdges(source);

            for (TrainEdge<Station> route : routes) {
                // display route information
                System.out.printf(
                        "%-5d %-8s %-8s %8s     RM %7.2f%n",
                        number++,
                        source.getStationCode(),
                        route.getDestination().getStationCode(),
                        ConsoleUtils.formatDuration(route.getDuration()),
                        route.getPrice()
                );
            }
        }
        // divider
        ConsoleUtils.printLine();
        System.out.println("Total Routes: " + (number - 1));
        ConsoleUtils.pause(scanner);
    }
}
