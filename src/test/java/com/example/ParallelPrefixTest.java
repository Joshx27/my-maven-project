package com.example;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class ParallelPrefixTest {

    @Test
    public void testParallelPrefixSumWithPositiveNumbers() {
        int[] inputArray = { 1, 2, 3, 4, 5, 6, 7, 8 };
        int[] expectedOutput = { 1, 3, 6, 10, 15, 21, 28, 36 };

        int[] result = ParallelPrefix.parallelPrefix(inputArray);
        assertArrayEquals(expectedOutput, result);
    }

    @Test
    public void testParallelPrefixSumWithNegativeNumbers() {
        int[] inputArray = { -1, -2, -3, -4, -5 };
        int[] expectedOutput = { -1, -3, -6, -10, -15 };

        int[] result = ParallelPrefix.parallelPrefix(inputArray);
        assertArrayEquals(expectedOutput, result);
    }

    @Test
    public void testParallelPrefixSumWithMixedNumbers() {
        int[] inputArray = { 1, -2, 3, -4, 5, -6 };
        int[] expectedOutput = { 1, -1, 2, -2, 3, -3 };

        int[] result = ParallelPrefix.parallelPrefix(inputArray);
        assertArrayEquals(expectedOutput, result);
    }

    @Test
    public void testParallelPrefixSumWithSingleElementArray() {
        int[] inputArray = { 42 };
        int[] expectedOutput = { 42 };

        int[] result = ParallelPrefix.parallelPrefix(inputArray);
        assertArrayEquals(expectedOutput, result);
    }

    @Test
    public void testPpxLen0() {
        int[] A = new int[] {};
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] {}, G);
    }

    @Test
    public void testPpxLen1() {
        int[] A = new int[] { 1 };
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] { 1 }, G);
    }

    @Test
    public void testPpxLen2_1() {
        int[] A = new int[] { 1, 1 };
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] { 1, 2 }, G);
    }

    @Test
    public void testPpxLen2_2() {
        int[] A = new int[] { 0, 1 };
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] { 0, 1 }, G);
    }

    @Test
    public void testPpxLen4_1() {
        int[] A = new int[] { -1, -2, -3, -4 };
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] { -1, -3, -6, -10 }, G);
    }

    @Test
    public void testPpxLen8_1() {
        int[] A = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] { 1, 3, 6, 10, 15, 21, 28, 36 }, G);
    }

    @Test
    public void testPpxLen8_2() {
        int[] A = new int[] { 0, 0, 1, 0, 0, 0, 0, 0 };
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] { 0, 0, 1, 1, 1, 1, 1, 1 }, G);
    }

    @Test
    public void testPpxLen8_3() {
        int[] A = new int[] { 0, 0, 1, 0, 0, -1, 0, 0 };
        int[] G = ParallelPrefix.parallelPrefix(A);
        assertArrayEquals(new int[] { 0, 0, 1, 1, 1, 0, 0, 0 }, G);
    }

}