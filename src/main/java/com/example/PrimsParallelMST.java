package com.example;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PrimsParallelMST {
    public static void main(String[] args) {
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
}
