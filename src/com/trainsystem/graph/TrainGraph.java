package com.trainsystem.graph;

import com.trainsystem.model.Station;

public class TrainGraph extends AbstractGraph<Station, TrainEdge<Station>> {

    public boolean addEdge(Station source, Station destination, int duration, double price) {
        // check whether the source and destination vertex exists in the graph or not
        if (!containsVertex(source) || !containsVertex(destination)) {
            // vertex not found
            return false;
        }

        // check whether this edge already exists or not
        if (containsEdge(source, destination)) {
            // already contain, can't add again
            return false;
        }

        // the graph didn't contain the edge
        // create a new edge obj
        TrainEdge<Station> newEdge = new TrainEdge<>(destination, duration, price);

        // add inside the graph
        adjacencyList.get(source).add(newEdge);

        return true;
    }
}
