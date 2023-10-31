package com.example;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class ParallelPrefixTest {

    @Test
    public void testParallelPrefixSumWithPositiveNumbers() {
        int[] inputArray = { 1, 2, 3, 4, 5, 6, 7, 8 };
        int[] expectedOutput = { 1, 3, 6, 10, 15, 21, 28, 36 };

        int[] result = ParallelPrefix.parallelPrefixSum(inputArray);
        assertArrayEquals(expectedOutput, result);
    }

    @Test
    public void testParallelPrefixSumWithNegativeNumbers() {
        int[] inputArray = { -1, -2, -3, -4, -5 };
        int[] expectedOutput = { -1, -3, -6, -10, -15 };

        int[] result = ParallelPrefix.parallelPrefixSum(inputArray);
        assertArrayEquals(expectedOutput, result);
    }

    @Test
    public void testParallelPrefixSumWithMixedNumbers() {
        int[] inputArray = { 1, -2, 3, -4, 5, -6 };
        int[] expectedOutput = { 1, -1, 2, -2, 3, -3 };

        int[] result = ParallelPrefix.parallelPrefixSum(inputArray);
        assertArrayEquals(expectedOutput, result);
    }

    @Test
    public void testParallelPrefixSumWithSingleElementArray() {
        int[] inputArray = { 42 };
        int[] expectedOutput = { 42 };

        int[] result = ParallelPrefix.parallelPrefixSum(inputArray);
        assertArrayEquals(expectedOutput, result);
    }
}
