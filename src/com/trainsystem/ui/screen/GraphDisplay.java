package com.trainsystem.ui.screen;

import com.trainsystem.graph.TrainGraph;
import com.trainsystem.ui.UIHelper;

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
        UIHelper.printTitle("DISPLAY TRAIN NETWORK");

        // show information of graph
        System.out.println("Opening train network visualization...");
        System.out.println();
        System.out.println("Stations : " + graph.getSize());
        System.out.println("Routes   : " + graph.getEdgeCount());

        // create new window and display the graph
//        showGraph();

        // show info
        UIHelper.printInfo(
                "Train network opened in a new window."
        );

        UIHelper.pause(scanner);
    }

    // to display the entire graph in new window
    private void showGraph() {

    }
}
