package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainGraph;
import com.trainsystem.ui.utils.ConsoleUtils;

import java.util.Scanner;

public class GraphDisplay {

    // scanner and graph passed by main
    private final Scanner scanner;
    private final TrainGraph graph;

    public GraphDisplay(Scanner scanner, TrainGraph graph) {
        this.scanner = scanner;
        this.graph = graph;
    }

    /**
     * display graph entrance
     */
    public void start() {
        // title
        ConsoleUtils.printTitle("DISPLAY TRAIN NETWORK");

        // show information of graph
        System.out.println("Opening train network visualization...");
        System.out.println();
        System.out.println("Stations : " + graph.getSize());
        System.out.println("Routes   : " + graph.getEdgeCount());

        // create new window and display the graph
//        showGraph();

        // show info
        ConsoleUtils.printInfo(
                "Train network opened in a new window."
        );

        ConsoleUtils.pause(scanner);
    }

    // to display the entire graph in new window
    private void showGraph() {

    }
}
