package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.RecursiveTask;

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

        // Add edges for vertex 0 to the priority queue
        visited[0] = true;
        for (Edge edge : graph.get(0)) {
            priorityQueue.add(edge);
        }

        // Add all other edges to the priority queue
        for (int i = 1; i < numVertices; i++) {
            for (Edge edge : graph.get(i)) {
                priorityQueue.add(edge);
            }
        }

        while (minimumSpanningTree.size() < numVertices - 1) {
            Edge minEdge = findMinimumEdge(graph, visited, priorityQueue);

            if (minEdge == null) {
                // Handle the case where no minimum edge was found
                break;
            }

            int to = minEdge.to;

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

    private static Edge findMinimumEdge(List<List<Edge>> graph, boolean[] visited, PriorityQueue<Edge> priorityQueue) {
        if (priorityQueue.isEmpty()) {
            // Handle the case where the queue is empty
            return null;
        }
        // Parallelize the process of finding the minimum weighted edge
        return new FindMinimumEdgeTask(graph, visited, priorityQueue, 0, priorityQueue.size()).invoke();
    }

    private static class FindMinimumEdgeTask extends RecursiveTask<Edge> {
        private List<List<Edge>> graph;
        private boolean[] visited;
        private PriorityQueue<Edge> priorityQueue;
        private int start;
        private int end;

        public FindMinimumEdgeTask(List<List<Edge>> graph, boolean[] visited, PriorityQueue<Edge> priorityQueue,
                int start, int end) {
            this.graph = graph;
            this.visited = visited;
            this.priorityQueue = priorityQueue;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Edge compute() {
            if (end - start <= 1) {
                return priorityQueue.poll();
            } else {
                int middle = start + (end - start) / 2;

                FindMinimumEdgeTask leftTask = new FindMinimumEdgeTask(graph, visited, priorityQueue, start, middle);
                FindMinimumEdgeTask rightTask = new FindMinimumEdgeTask(graph, visited, priorityQueue, middle, end);

                invokeAll(leftTask, rightTask);

                Edge leftMin = leftTask.join();
                Edge rightMin = rightTask.join();

                return (leftMin.compareTo(rightMin) < 0) ? leftMin : rightMin;
            }
        }
    }

    public static void main(String[] args) {
        int numVertices = 5;
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges to the graph
        graph.get(0).add(new Edge(1, 1));
        graph.get(1).add(new Edge(0, 1));
        graph.get(1).add(new Edge(2, 2));
        graph.get(2).add(new Edge(1, 2));
        graph.get(2).add(new Edge(3, 3));
        graph.get(3).add(new Edge(2, 3));
        graph.get(3).add(new Edge(4, 4));
        graph.get(4).add(new Edge(3, 4));
        graph.get(0).add(new Edge(4, 5));
        graph.get(4).add(new Edge(0, 5));

        List<Edge> minimumSpanningTree = primMST(graph);

        System.out.println("Minimum Spanning Tree Edges:");
        for (Edge edge : minimumSpanningTree) {
            System.out.println("(to: " + edge.to + ", weight: " + edge.weight + ")");
        }
    }
}
