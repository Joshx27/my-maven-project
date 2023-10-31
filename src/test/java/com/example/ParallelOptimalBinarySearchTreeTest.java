package com.example;

import org.junit.Test;

import static com.example.ParallelOptimalBinarySearchTree.ParallelOptimalSearchTree;
import static org.junit.Assert.assertEquals;

public class ParallelOptimalBinarySearchTreeTest {
    @Test
    public void test1() {
        int[] frequencies = { 34, 50 };
        assertEquals(118, ParallelOptimalSearchTree(frequencies));
    }

    @Test
    public void test2() {
        int[] frequencies = { 34, 8, 50 };
        assertEquals(142, ParallelOptimalSearchTree(frequencies));
    }

    @Test
    public void test3() {
        int[] frequencies = { 4, 2, 6, 3 };
        assertEquals(26, ParallelOptimalSearchTree(frequencies));
    }

    @Test
    public void test4() {
        int[] frequencies = { 25, 20, 5, 20, 30 };
        assertEquals(210, ParallelOptimalSearchTree(frequencies));
    }

}
