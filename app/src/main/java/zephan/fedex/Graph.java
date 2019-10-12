package zephan.fedex;

import java.util.*;

public class Graph {
    private int vertices;

    int startVertex;
    int endVertex;

    Node[] nodes;

    LinkedList<Integer> path = new LinkedList<>();

    Graph(int size, Node[] nodes, int startVertex, int endVertex) {

        vertices = size;
        this.nodes = nodes;

        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    void BFS(int startVertex, int endVertex) {

        boolean alreadyVisited[] = new boolean[vertices];

        Queue<Integer> queue = new LinkedList<>();
//		Say we've already visited the node we're starting on
        alreadyVisited[startVertex] = true;
//		Add in the startNode to the queue
        queue.add(startVertex);
//		Run this until all Vertices have been indexed
        int currentVertex;

        while (!queue.isEmpty()) {
            currentVertex = queue.poll();
            LinkedList<Integer> verticesToSearch = nodes[currentVertex].getAdjacencyList();
            int currentDistance = nodes[currentVertex].getDistance();
            for (int j = 0; j < 4; j++) {
                int poppedVertex = verticesToSearch.pop();
                if (!alreadyVisited[poppedVertex]) {
                    alreadyVisited[poppedVertex] = true;
                    queue.add(poppedVertex);
                    nodes[poppedVertex].setDistance(currentDistance + 1);
                    nodes[poppedVertex].setPreviousNode(currentVertex);
                }
            }

            if (nodes[currentVertex].getIndex() == endVertex)
                break;
        }
    }

    void findPath() {
        BFS(startVertex, endVertex);

        int nextNode = endVertex;

        while (nodes[nextNode].getPreviousNode() != -1) {
            path.push(nodes[nextNode].getPreviousNode());
            nextNode = nodes[nextNode].getPreviousNode();
        }

    }

    public LinkedList<Integer> getPath() {
        return path;
    }
}

