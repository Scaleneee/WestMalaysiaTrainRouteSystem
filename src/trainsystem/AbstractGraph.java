package trainsystem;

import java.util.*;

public class AbstractGraph<V> implements Graph<V> {

    // adjacency list
    // vertex -> list of outgoing edges
    protected Map<V, List<TrainEdge<V>>> adjacencyList = new LinkedHashMap<>();

    /*
        Vertex Operation Methods
     */
    @Override
    public int getSize() {
        return adjacencyList.size();
    }

    @Override
    public Set<V> getVertices() {
        return adjacencyList.keySet();
    }

    @Override
    public boolean containsVertex(V vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean addVertex(V vertex) {
        // if already exists
        if (containsVertex(vertex)) {
            return false;
        }

        // if not exists yet
        adjacencyList.put(vertex, new ArrayList<>());

        return true;
    }

    @Override
    public boolean removeVertex(V vertex) {
        // if the vertex no exists in graph
        if (!containsVertex(vertex)) {
            return false;
        }

        // remove the vertex
        adjacencyList.remove(vertex);

        // remove all edge pointing to the vertex
        for (List<TrainEdge<V>> edges : adjacencyList.values()) {
            edges.removeIf( edge ->
                    edge.getDestination().equals(vertex)
            );
        }

        // remove success
        return true;
    }

    /*
        Edges Operation Methods
     */
    @Override
    public boolean containsEdge(V source, V destination) {
        // if source and destination vertex didn't exists
        if (!containsVertex(source) || !containsVertex(destination)) {
            return false;
        }

        for (TrainEdge<V> edges : adjacencyList.get(source)) {
            if (edges.getDestination().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addEdge(V source, V destination, int duration, double price, int distance) {
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
        TrainEdge<V> newEdge = new TrainEdge<>(destination, duration, price, distance);

        // add inside the graph
        adjacencyList.get(source).add(newEdge);

        return true;
    }

    @Override
    public boolean removeEdge(V source, V destination) {
        // check whether the source and destination vertex exists in the graph or not
        if (!containsVertex(source) || !containsVertex(destination)) {
            // vertex not found
            return false;
        }

        // remove the edge
        adjacencyList.get(source).removeIf( edge ->
                edge.getDestination().equals(destination)
        );

        return true;
    }

    /*
        Neighbors Operation Methods
     */
    @Override
    public List<V> getNeighbors(V vertex) {
        List<V> neighbors = new ArrayList<>();

        // check whether the vertex exists in graph or not
        if (!containsVertex(vertex)) {
            return neighbors;
        }

        for (TrainEdge<V> edge : adjacencyList.get(vertex)) {
            neighbors.add(edge.getDestination());
        }
        return neighbors;
    }

    @Override
    public int getDegree(V vertex) {
        // check whether the vertex exists in graph or not
        if (!containsVertex(vertex)) {
            return 0;
        }
        // return out-degree
        return adjacencyList.get(vertex).size();
    }

    /*
        Display Operation Methods
     */
    @Override
    public void printGraph() {
        for (V vertex : adjacencyList.keySet()) {
            System.out.print(vertex + " -> ");

            // the edges list of the vertex
            List<TrainEdge<V>> edges = adjacencyList.get(vertex);

            // display
            for (TrainEdge<V> edge : edges) {
                System.out.print(edge.getDestination() + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void clear() {
        adjacencyList.clear();
    }

    /*
        Tree class
     */
    public static class Tree<V> {

    }
}
