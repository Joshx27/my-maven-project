package com.example;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PrimsParallelMST {

    // number of nodes
    int n;

    // edges assigned to each node
    List<List<Integer>> edges;

    // vector containing edges for the minimum spanning tree
    int[] G;

    public PrimsParallelMST(int n, List<List<Integer>> edges, int[] G) {
        this.n = n;
        this.edges = edges;
        G = new int[n];
        this.G = G;
    }

    boolean fixed(int j, int[] G) {

    }

    boolean forbidden(int j) {

    }

    void advance(int j) {

    }

    int[] LLPPrim() {
        // Create an ExecutorService with a single thread
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // Define a task to be executed repeatedly
        Runnable alwaysTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("This task runs always!");
                // You can put your code here
            }
        };

        // Schedule the task to run repeatedly with a fixed delay
        executor.scheduleWithFixedDelay(alwaysTask, 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {

    }

}
