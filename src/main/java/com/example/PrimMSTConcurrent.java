package com.example;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class MST {
    private int V;

    public MST(int vertices) {
        this.V = vertices;
    }

    int minKey(int key[], Boolean mstSet[]) {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        ParallelMinKey parallelMinKey = new ParallelMinKey(key, mstSet, 0, V);
        ForkJoinPool pool = new ForkJoinPool();
        min_index = pool.invoke(parallelMinKey);

        return min_index;
    }

    void printMST(int parent[], int graph[][]) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }

    void primMST(int graph[][]) {
        int parent[] = new int[V];
        int key[] = new int[V];
        Boolean mstSet[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;

            ParallelUpdateKeys parallelUpdateKeys = new ParallelUpdateKeys(key, mstSet, u, graph);
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(parallelUpdateKeys);
        }

        printMST(parent, graph);
    }

    public static void main(String[] args) {
        MST t = new MST(5);
        int graph[][] = new int[][] {
                { 0, 2, 0, 6, 0 },
                { 2, 0, 3, 8, 5 },
                { 0, 3, 0, 0, 7 },
                { 6, 8, 0, 0, 9 },
                { 0, 5, 7, 9, 0 }
        };

        t.primMST(graph);
    }
}

class ParallelMinKey extends RecursiveTask<Integer> {
    private int[] key;
    private Boolean[] mstSet;
    private int low;
    private int high;

    public ParallelMinKey(int[] key, Boolean[] mstSet, int low, int high) {
        this.key = key;
        this.mstSet = mstSet;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        if (low < high) {
            if (high - low <= 5) {
                for (int v = low; v < high; v++) {
                    if (!mstSet[v] && key[v] < min) {
                        min = key[v];
                        min_index = v;
                    }
                }
            } else {
                int mid = (low + high) / 2;
                ParallelMinKey left = new ParallelMinKey(key, mstSet, low, mid);
                ParallelMinKey right = new ParallelMinKey(key, mstSet, mid, high);

                left.fork();
                int rightMin = right.compute();
                int leftMin = left.join();

                if (leftMin != -1 && (rightMin == -1 || key[leftMin] < key[rightMin])) {
                    min = key[leftMin];
                    min_index = leftMin;
                } else {
                    min = key[rightMin];
                    min_index = rightMin;
                }
            }
        }

        return min_index;
    }
}

class ParallelUpdateKeys extends RecursiveAction {
    private int[] key;
    private Boolean[] mstSet;
    private int u;
    private int[][] graph;
    private int low;
    private int high;

    public ParallelUpdateKeys(int[] key, Boolean[] mstSet, int u, int[][] graph) {
        this.key = key;
        this.mstSet = mstSet;
        this.u = u;
        this.graph = graph;
        this.low = 0;
        this.high = key.length;
    }

    private ParallelUpdateKeys(int[] key, Boolean[] mstSet, int u, int[][] graph, int low, int high) {
        this.key = key;
        this.mstSet = mstSet;
        this.u = u;
        this.graph = graph;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (high - low <= 5) {
            for (int v = low; v < high; v++) {
                if (!mstSet[v] && graph[u][v] != 0 && graph[u][v] < key[v]) {
                    key[v] = graph[u][v];
                }
            }
        } else {
            int mid = (low + high) / 2;
            ParallelUpdateKeys left = new ParallelUpdateKeys(key, mstSet, u, graph, low, mid);
            ParallelUpdateKeys right = new ParallelUpdateKeys(key, mstSet, u, graph, mid, high);

            left.fork();
            right.invoke();
            left.join();
        }
    }
}
