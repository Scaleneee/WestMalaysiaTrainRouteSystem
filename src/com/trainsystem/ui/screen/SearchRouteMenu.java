package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainEdge;
import com.trainsystem.graph.TrainGraph;
import com.trainsystem.model.Station;
import com.trainsystem.ui.utils.ConsoleUtils;
import com.trainsystem.ui.utils.InputUtils;

import java.util.List;
import java.util.Scanner;

public class SearchRouteMenu {

    //scanner and graph pass by main
    private final Scanner scanner;
    private final TrainGraph graph;

    public SearchRouteMenu(Scanner scanner, TrainGraph graph){
        this.scanner = scanner;
        this.graph = graph;
    }

    //search train route screen entrance
    public void start(){

        // ask user input
        int choice = 0;
        do {
            // display title
            ConsoleUtils.printTitle("Search Train Routes");

            // display menu choices
            System.out.println("1. Fewest Station Connections (BFS)");
            System.out.println("2. Fatest Route (Dijkstra)");
            System.out.println("3. Cheapest Route (Dijkstra)");
            System.out.println("0. Back\n");

            // get user input
            choice = InputUtils.getMenuChoice(scanner, 3);

            switch (choice) {
                case 1:
                    // bfs
                    searchScreen("bfs");
                    break;
                case 2:
                    // fastest route
                    searchScreen("fastest");
                    break;
                case 3:
                    // cheapest route
                    searchScreen("cheapest");
                    break;
                default:
                    // choice = 0, back
                    return;
            }
        } while (choice != 0);
    }

    /**
     * perform search and display the result
     * @param searchMethod bfs, fastest, cheapest
     */
    private void searchScreen(String searchMethod) {
        // set the title based on the search method
        String title = searchMethod.equals("bfs") ? "Fewest Station Connections" :
                searchMethod.equals("fastest") ? "Fastest Train Route" :
                        "Cheapest Train Route";
        // display title
        ConsoleUtils.printTitle(title);

        // ask user input the start and destination station code
        System.out.print("Enter Source Station Code: ");
        String sourceCode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter Destination Station Code: ");
        String destinationCode = scanner.nextLine().trim().toUpperCase();

        // get station by station code
        Station source = graph.getStationByCode(sourceCode);
        Station destination = graph.getStationByCode(destinationCode);

        // if one of the station not found
        if (source == null || destination == null) {
            // show error msg
            ConsoleUtils.printError("Source or destination station not found.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // if source and destination station are same
        if (source.equals(destination)) {
            // show error msg
            ConsoleUtils.printError("Source and destination station cannot be the same.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // perform search algorithm based on the search method
        List<Station> path = searchMethod.equals("bfs") ? graph.bfs(source, destination) :
                searchMethod.equals("fastest") ? graph.findFastestRoute(source, destination) :
                graph.findCheapestRoute(source, destination);

        // route not found
        if (path.isEmpty()) {
            // show error msg
            ConsoleUtils.printError("No route found between the selected stations.");
            ConsoleUtils.pause(scanner);
            return;
        }

        // route found
        // set the success message based on the search method
        String successMessage = searchMethod.equals("bfs") ? "Route with fewest connections found.\n" :
                searchMethod.equals("fastest") ? "Fastest route found.\n" :
                        "Cheapest route found.\n";
        ConsoleUtils.printSuccess(successMessage);

        // display result
        displaySearchResult(path);
    }

    private void displayPath(List<Station> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getStationCode());
            if (i != path.size() - 1) {
                // if not the last station to display
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    private void displaySearchResult(List<Station> path) {
        // use for calculation
        int duration = 0;
        double totalFare = 0.0;

        // display path
        System.out.println("Route: ");
        displayPath(path);

        // for temporarily storage
        Station source;
        Station destination;
        for (int i = 0; i < path.size(); i++) {
            // if it was the last stations of the path
            if (i == path.size() - 1) {
                // end
                break;
            }
            source = path.get(i);
            destination = path.get(i + 1);

            // get the edge
            TrainEdge<Station> edge = graph.getEdge(source, destination);

            // calculation
            duration += edge.getDuration();
            totalFare += edge.getPrice();
        }
        // display the information
        System.out.println("No. of routes: " + (path.size() - 1));
        System.out.println("Total Duration: " + ConsoleUtils.formatDuration(duration));
        System.out.printf("Total Fare: RM %.2f%n", totalFare);
    }
}
