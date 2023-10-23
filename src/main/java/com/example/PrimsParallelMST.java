package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Edge implements Comparable<Edge> {
    int to, weight;

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

public class PrimsParallelMST {

    public static List<Edge> primMST(List<List<Edge>> graph) {
        int numVertices = graph.size();
        List<Edge> minimumSpanningTree = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        // Start with the first vertex (vertex 0)
        visited[0] = true;

        for (Edge edge : graph.get(0)) {
            priorityQueue.add(edge);
        }

        while (minimumSpanningTree.size() < numVertices - 1) {
            Edge minEdge = priorityQueue.poll();
            int to = minEdge.to;

            if (visited[to]) {
                continue;
            }

            visited[to] = true;
            minimumSpanningTree.add(minEdge);

            for (Edge edge : graph.get(to)) {
                if (!visited[edge.to]) {
                    priorityQueue.add(edge);
                }
            }
        }

        return minimumSpanningTree;
    }

    public static void main(String[] args) {
        int numVertices = 4;
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges to the graph
        graph.get(0).add(new Edge(1, 2));
        graph.get(1).add(new Edge(0, 2));
        graph.get(1).add(new Edge(2, 3));
        graph.get(2).add(new Edge(1, 3));
        graph.get(0).add(new Edge(3, 4));
        graph.get(3).add(new Edge(0, 4));

        List<Edge> minimumSpanningTree = primMST(graph);

        System.out.println("Minimum Spanning Tree Edges:");
        for (Edge edge : minimumSpanningTree) {
            System.out.println("(to: " + edge.to + ", weight: " + edge.weight + ")");
        }
    }

}
