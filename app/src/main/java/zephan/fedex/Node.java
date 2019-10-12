package zephan.fedex;

import java.io.*;
import java.util.*;

public class Node {
    private int distance;
    private int index;
    private int previousNode;
    private int connections;
    private String city;
    private LinkedList<Integer> path[];
    private LinkedList<Integer> adjacencyList;

    Node(String city, int index, int size) {
        connections = size;
        adjacencyList = new LinkedList<Integer>();
        this.index = index;
        this.city = city;
        previousNode = -1;
    }

    // Function to add an edge into the graph
    void addEdge(int y) {
        if (!adjacencyList.contains(y))
            adjacencyList.add(y);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LinkedList<Integer>[] getPath() {
        return path;
    }

    public void setPath(LinkedList<Integer>[] path) {
        this.path = path;
    }

    public LinkedList<Integer> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(LinkedList<Integer> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public int getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(int previousNode) {
        this.previousNode = previousNode;
    }
}
