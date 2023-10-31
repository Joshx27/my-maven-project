package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class PrimMSTConcurrent {

    public static class Edge {
        int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static class Graph {
        List<Edge> edges;

        public Graph() {
            edges = new ArrayList<>();
        }

        public void addEdge(int from, int to, int weight) {
            edges.add(new Edge(from, to, weight));
            edges.add(new Edge(to, from, weight)); // Undirected graph
        }

        public int getVertexCount() {
            int vertexCount = 0;
            for (Edge edge : edges) {
                vertexCount = Math.max(vertexCount, Math.max(edge.from, edge.to) + 1);
            }
            return vertexCount;
        }
    }

    public static class PrimTask extends RecursiveTask<Integer> {
        Graph graph;
        int root;

        public PrimTask(Graph graph, int root) {
            this.graph = graph;
            this.root = root;
        }

        @Override
        protected Integer compute() {
            int numVertices = graph.getVertexCount();
            boolean[] inMST = new boolean[numVertices];
            int totalWeight = 0;

            PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
            inMST[root] = true;

            for (Edge edge : graph.edges) {
                if (edge.from == root) {
                    minHeap.add(edge);
                }
            }

            while (!minHeap.isEmpty()) {
                Edge minEdge = minHeap.poll();
                if (!inMST[minEdge.to]) {
                    inMST[minEdge.to] = true;
                    totalWeight += minEdge.weight;

                    for (Edge edge : graph.edges) {
                        if (edge.from == minEdge.to && !inMST[edge.to]) {
                            minHeap.add(edge);
                        }
                    }
                }
            }

            return totalWeight;
        }
    }

    public static int parallelLLPPrim(Graph graph) {
        ForkJoinPool pool = new ForkJoinPool();
        int totalWeight = pool.invoke(new PrimTask(graph, 0));
        return totalWeight;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();

        List<Edge> graphEdges = new ArrayList<>();
        graphEdges.add(new Edge(0, 1, 10));
        graphEdges.add(new Edge(0, 2, 6));
        graphEdges.add(new Edge(0, 3, 5));
        graphEdges.add(new Edge(1, 3, 15));
        graphEdges.add(new Edge(2, 3, 4));

        for (Edge edge : graphEdges) {
            graph.addEdge(edge.from, edge.to, edge.weight);
        }

        int minWeight = parallelLLPPrim(graph);
        System.out.println("Minimum Spanning Tree Weight: " + minWeight);
    }

}
