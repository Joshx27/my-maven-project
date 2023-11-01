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
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);

        for (int i = 1; i < n; i++) {
            final int currentIndex = i;
            Callable<Integer> task = () -> {
                output[currentIndex] = input[currentIndex] + output[currentIndex - 1];
                return output[currentIndex];
            };
            futures.add(completionService.submit(task));
        }

        // Wait for tasks to complete and retrieve results in order
        for (int i = 0; i < n - 1; i++) {
            try {
                Future<Integer> future = completionService.take();
                future.get(); // Ensure task is completed
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
