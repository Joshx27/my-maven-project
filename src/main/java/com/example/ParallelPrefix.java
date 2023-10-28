package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelPrefix {

    public static int[] parallelPrefixSum(int[] input) {
        int n = input.length;
        int[] output = new int[n];
        output[0] = input[0];

        ExecutorService executor = Executors.newFixedThreadPool(n);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            final int currentIndex = i;
            Callable<Integer> task = () -> {
                output[currentIndex] = input[currentIndex] + output[currentIndex - 1];
                return output[currentIndex];
            };
            futures.add(executor.submit(task));
        }

        for (Future<Integer> future : futures) {
            try {
                future.get(); // Wait for tasks to complete
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return output;
    }

    public static void main(String[] args) {
        int[] inputArray = { 1, 2, 3, 4, 5, 6, 7, 8 };

        int[] result = parallelPrefixSum(inputArray);

        System.out.println("Input Array: ");
        for (int num : inputArray) {
            System.out.print(num + " ");
        }

        System.out.println("\nPrefix Sum: ");
        for (int num : result) {
            System.out.print(num + " ");
        }
    }
}
