package trainsystem;

public class TrainEdge<V> {
    // the source will always be the vertex in the adjacency list
    // store the destination of the graph
    private V destination;

    // store the duration in minutes
    private int duration;

    // ticket price from vertex to the destination
    private double price;

    // store the distance in kilometers (km)
    private int distance;

    // constructor
    public TrainEdge(V destination, int duration, double price, int distance) {
        this.destination = destination;
        this.duration = duration;
        this.price = price;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }
}
