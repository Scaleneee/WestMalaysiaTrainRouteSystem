package com.trainsystem.graph;

import java.util.*;

public class AbstractGraph<V, E extends Edge<V>> implements Graph<V, E> {

    // adjacency list
    // vertex -> list of outgoing edges
    protected Map<V, List<E>> adjacencyList = new LinkedHashMap<>();

    /*
        Vertex Operation Methods
     */
    @Override
    public int getSize() {
        return adjacencyList.size();
    }

    @Override
    public Set<V> getVertices() {
        // don't allow others to modify the set
        return Collections.unmodifiableSet(adjacencyList.keySet());
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
        for (List<E> edges : adjacencyList.values()) {
            edges.removeIf(edge ->
                    edge.getDestination().equals(vertex)
            );
        }

        // remove success
        return true;
    }

    @Override
    public int getEdgeCount() {
        // store the edge count
        int count = 0;

        for (V vertex : getVertices()) {
            // get all the edge of each vertex
            List<E> edges = adjacencyList.get(vertex);
            count += edges.size();
        }
        return count;
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

        for (E edge : adjacencyList.get(source)) {
            if (edge.getDestination().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addEdge(V source, E edge) {
        // check whether the source and destination vertex exists in the graph or not
        if (!containsVertex(source) || !containsVertex(edge.getDestination())) {
            // vertex not found
            return false;
        }

        // check whether this edge already exists or not
        if (containsEdge(source, edge.getDestination())) {
            // already contain, can't add again
            return false;
        }

        // add edge into the adjacency list
        adjacencyList.get(source).add(edge);
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
        adjacencyList.get(source).removeIf(edge ->
                edge.getDestination().equals(destination)
        );

        return true;
    }

    @Override
    public E getEdge(V source, V destination) {
        // if source or destination not found
        if (!containsVertex(source) || !containsVertex(destination)) {
            return null;
        }

        for (E edge : adjacencyList.get(source)) {
            if (edge.getDestination().equals(destination)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public List<E> getEdges(V source) {
        if (!containsVertex(source)) {
            // vertex not found, return a empty list
            return new ArrayList<>();
        }
        // don't allow others to modify the list
        return Collections.unmodifiableList(adjacencyList.get(source));
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

        for (E edge : adjacencyList.get(vertex)) {
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
            List<E> edges = adjacencyList.get(vertex);

            // display
            for (E edge : edges) {
                System.out.print(edge.getDestination() + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void clear() {
        adjacencyList.clear();
    }

    @Override
    public List<V> bfs(V start, V destination) {
        return List.of();
    }
}
