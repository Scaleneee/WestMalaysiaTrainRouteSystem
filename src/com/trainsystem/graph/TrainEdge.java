package com.trainsystem.graph;

public class TrainEdge<V> implements Edge<V> {
    // the source will always be the vertex in the adjacency list
    // store the destination of the graph
    private V destination;

    // store the duration in minutes
    private int duration;

    // ticket price from vertex to the destination
    private double price;

    // constructor
    public TrainEdge(V destination, int duration, double price) {
        this.destination = destination;
        this.duration = duration;
        this.price = price;
    }

    // getter methods
    public V getDestination() {
        return destination;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }
}
