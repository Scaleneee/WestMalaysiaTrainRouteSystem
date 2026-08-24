package com.trainsystem.graph;

import com.trainsystem.model.Station;

import java.util.*;

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

    public Station searchStation(String stationCode) {
        for (Station station : getVertices()) {
            if (station.getStationCode()
                    .equalsIgnoreCase(stationCode)) {
                return station;
            }
        }
        return null;
    }

    public Station getStationByCode(String stationCode) {
        for (Station station : getVertices()) {
            if (station.getStationCode().equalsIgnoreCase(stationCode)) {
                return station;
            }
        }
        return null;
    }

    /**
     * Dijkstra search algorithm based on duration, find the fastest route
     *
     * @param start       station
     * @param destination station
     * @return list path
     */
    public List<Station> findFastestRoute(Station start, Station destination) {
        // empty path
        List<Station> emptyPath = new ArrayList<>();

        // validate vertices
        if (!containsVertex(start) || !containsVertex(destination)) {
            // start or destination does not exist
            // return empty list
            return emptyPath;
        }

        // store shortest duration to each station
        Map<Station, Double> duration = new HashMap<>();

        // store parent for path reconstruction
        Map<Station, Station> parent = new HashMap<>();

        // smallest duration will be removed first
        // queue the RouteNode inside the PriorityQueue based on the cost
        PriorityQueue<RouteNode> queue = new PriorityQueue<>(Comparator.comparingDouble(RouteNode::cost));

        // initialize all stations duration to infinity
        for (Station station : getVertices()) {
            duration.put(station, Double.POSITIVE_INFINITY);
        }

        // starting station has 0 duration
        duration.put(start, 0.0);

        queue.offer(new RouteNode(start, 0.0));

        while (!queue.isEmpty()) {
            RouteNode node = queue.poll();

            Station current = node.station();
            double currentDuration = node.cost();

            // ignored outdated queue entry
            if (currentDuration > duration.get(current)) {
                continue;
            }

            // destination found
            if (current.equals(destination)) {
                break;
            }

            // check all outgoing routes
            for (TrainEdge<Station> edge : getEdges(current)) {
                Station neighbor = edge.getDestination();

                double newDuration = duration.get(current) + edge.getDuration();

                // shorter route found
                if (newDuration < duration.get(neighbor)) {
                    duration.put(neighbor, newDuration);
                    parent.put(neighbor, current);
                    queue.offer(new RouteNode(neighbor, newDuration));
                }
            }
        }
        // destination is unreachable
        if (Double.isInfinite(duration.get(destination))) {
            return emptyPath;
        }
        return buildPath(parent, start, destination);
    }

    /**
     * Dijkstra search algorithm based on ticket fare, find the cheapest route
     *
     * @param start       station
     * @param destination station
     * @return list path
     */
    public List<Station> findCheapestRoute(Station start, Station destination) {

        List<Station> emptyPath = new ArrayList<>();

        // validate vertices
        if (!containsVertex(start) || !containsVertex(destination)) {
            return emptyPath;
        }

        // store cheapest cost to each station
        Map<Station, Double> fare = new HashMap<>();

        // store parent for path reconstruction
        Map<Station, Station> parent = new HashMap<>();

        // smallest fare will be removed first
        PriorityQueue<RouteNode> queue = new PriorityQueue<>(Comparator.comparingDouble(RouteNode::cost));

        // initialize all stations to infinity
        for (Station station : getVertices()) {
            fare.put(station, Double.POSITIVE_INFINITY);
        }

        // starting station has RM 0
        fare.put(start, 0.0);

        queue.offer(new RouteNode(start, 0.0));

        while (!queue.isEmpty()) {
            RouteNode node = queue.poll();

            Station current = node.station();
            double currentFare = node.cost();

            // ignore outdated queue entry
            if (currentFare > fare.get(current)) {
                continue;
            }

            // destination found
            if (current.equals(destination)) {
                break;
            }

            // check all outgoing routes
            for (TrainEdge<Station> edge : getEdges(current)) {

                Station neighbor = edge.getDestination();

                double newFare = fare.get(current) + edge.getPrice();

                // cheaper route found
                if (newFare < fare.get(neighbor)) {
                    fare.put(neighbor, newFare);
                    parent.put(neighbor, current);
                    queue.offer(new RouteNode(neighbor, newFare));
                }
            }
        }

        // destination is unreachable
        if (Double.isInfinite(fare.get(destination))) {
            return emptyPath;
        }

        return buildPath(parent, start, destination);
    }

    private List<Station> buildPath(Map<Station, Station> parent, Station start, Station destination) {
        // use to store the path
        List<Station> path = new ArrayList<>();

        // start to construct the path from the destination
        Station current = destination;

        while (current != null) {
            path.add(current);

            // end
            if (current.equals(start)) {
                break;
            }
            // now current become their parent
            current = parent.get(current);
        }

        // reverse the order of the path list
        // from destination -> start become start -> destination
        Collections.reverse(path);

        // return the path result
        return path;
    }

    // helper record class
    private record RouteNode(Station station, double cost){}
}
