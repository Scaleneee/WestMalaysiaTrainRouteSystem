package com.trainsystem.graph;

import java.util.List;
import java.util.Set;

public interface Graph<V, E extends Edge<V>> {

    /*
        Vertex Operation Methods
     */
    // return the number of vertices
    int getSize();

    // return all vertices
    Set<V> getVertices();

    // check whether a vertex exists
    boolean containsVertex(V vertex);

    // add a new vertex
    boolean addVertex(V vertex);

    // remove a vertex
    boolean removeVertex(V vertex);

    /*
        Edges Operation Methods
     */
    // return the number of edges
    int getEdgeCount();

    // check whether an edge exists
    boolean containsEdge(V source, V destination);

    // add a directed edge
    boolean addEdge(V source, E edge);

    // remove a directed edge
    boolean removeEdge(V source, V destination);

    // return an edge, based on source and destination
    E getEdge(V source, V destination);

    // get all edges from the source
    List<E> getEdges(V source);

    /*
        Neighbors Operation Methods
     */
    // return all the neighbors of the vertex
    List<V> getNeighbors(V vertex);

    // return the number of the neighbors of the vertex
    // out-degree
    int getDegree(V vertex);

    // display all vertices and edges
    void printGraph();

    // remove all vertices and edges
    void clear();

    // graph traversal algorithm
    // BFS, get the shortest path result
    List<V> bfs(V start, V destination);

}