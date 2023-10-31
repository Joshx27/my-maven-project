package com.example;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ParallelOptimalBinarySearchTree {

    static int sum(int frequencies[], int i, int j) {
        int s = 0;
        for (int k = i; k <= j; k++) {
            s += frequencies[k];
        }
        return s;
    }

    static class OptimalSearchTreeTask extends RecursiveTask<Integer> {
        int frequencies[];
        int i, j;

        OptimalSearchTreeTask(int freq[], int i, int j) {
            this.frequencies = freq;
            this.i = i;
            this.j = j;
        }

        @Override
        protected Integer compute() {
            if (j < i) {
                return 0;
            }
            if (j == i) {
                return frequencies[i];
            }

            int fsum = sum(frequencies, i, j);
            int min = Integer.MAX_VALUE;

            for (int k = i; k <= j; ++k) {
                OptimalSearchTreeTask leftTask = new OptimalSearchTreeTask(frequencies, i, k - 1);
                OptimalSearchTreeTask rightTask = new OptimalSearchTreeTask(frequencies, k + 1, j);

                leftTask.fork();
                int cost = rightTask.invoke() + leftTask.join();

                if (cost < min) {
                    min = cost;
                }
            }

            return min + fsum;
        }
    }

    static int ParallelOptimalSearchTree(int frequencies[]) {
        int n = frequencies.length;
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new OptimalSearchTreeTask(frequencies, 0, n - 1));
    }

    public static void main(String[] args) {

        int[] frequencies = { 25, 20, 5, 20, 30 }; // Example frequencies
        int result = ParallelOptimalSearchTree(frequencies);

        System.out.println("Cost of Optimal BST is " + result);

    }
}