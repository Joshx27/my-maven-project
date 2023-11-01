package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class Edge {
    private int source;
    private int target;
    private int weight;

    public Edge(int source, int target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }
}

class bellmanFordAlgorithm {
    private int n; // Number of vertices
    private List<Edge> edges;
    private int[] distance;
    private static final int INF = Integer.MAX_VALUE;

    public bellmanFordAlgorithm(int n, List<Edge> edges) {
        this.n = n;
        this.edges = edges;
        distance = new int[n];
    }

    // initialize all distances to infinity, except the distance from the source
    // node to itself
    public void initialize(int source) {
        for (int i = 0; i < n; i++) {
            distance[i] = INF;
        }
        distance[source] = 0;
    }

    public void parallelLLPBellmanFord(int source) {
        initialize(source);
        int iterations = n - 1;
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new LLPBellmanFordTask(0, iterations));

        if (hasNegativeCycle()) {
            System.out.println("Negative cost cycle detected!");
        }
    }

    public boolean hasNegativeCycle() {
        for (Edge edge : edges) {
            int u = edge.getSource();
            int v = edge.getTarget();
            double weight = edge.getWeight();

            if (distance[u] != INF && distance[u] + weight < distance[v]) {
                return true;
            }
        }
        return false;
    }

    class LLPBellmanFordTask extends RecursiveTask<Void> {
        private int start;
        private int end;

        public LLPBellmanFordTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Void compute() {
            if (end - start <= 1) {
                // Sequentially relax edges
                relaxEdges();
            } else {
                int mid = start + (end - start) / 2;
                LLPBellmanFordTask left = new LLPBellmanFordTask(start, mid);
                LLPBellmanFordTask right = new LLPBellmanFordTask(mid, end);

                invokeAll(left, right);

                // Merge results if needed
                if (end - start > 2) {
                    relaxEdges();
                }
            }
            return null;
        }
    }

    public void relaxEdges() {
        for (Edge edge : edges) {
            int u = edge.getSource();
            int v = edge.getTarget();
            int weight = edge.getWeight();

            if (distance[u] != INF && distance[u] + weight < distance[v]) {
                distance[v] = distance[u] + weight;
            }
        }
    }

    public static int[] getBellmanFordPath(Integer[][] data, int source) {
        int n = data.length;
        List<Edge> edges = new ArrayList<>();
        for (int s = 0; s < data.length; s++) {
            for (int t = 0; t < data[s].length; t++) {
                if (data[s][t] != 0) {
                    edges.add(new Edge(s, t, data[s][t]));
                }
            }
        }
        bellmanFordAlgorithm bf = new bellmanFordAlgorithm(n, edges);
        bf.parallelLLPBellmanFord(source);
        return bf.distance;
    }

    public static void main(String[] args) {
        Integer[][] data = new Integer[][] {
                { 0, 1, 2, 0 },
                { 0, 0, 0, 3 },
                { 0, 0, 0, 4 },
                { 0, 0, 0, 0 } };
        int source = 0;
        int[] result = getBellmanFordPath(data, source);
        System.out.println(Arrays.toString(result));
    }
}