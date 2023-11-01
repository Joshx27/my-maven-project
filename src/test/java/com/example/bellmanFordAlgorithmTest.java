package com.example;

import static org.junit.Assert.assertArrayEquals;
import static com.example.bellmanFordAlgorithm.getBellmanFordPath;
import org.junit.Test;

public class bellmanFordAlgorithmTest {
    int INF = Integer.MAX_VALUE;

    @Test
    public void test1() {
        Integer[][] data = new Integer[][] {
                { 0, 1, 2, 0 },
                { 0, 0, 0, 3 },
                { 0, 0, 0, 4 },
                { 0, 0, 0, 0 } };
        int source = 0;
        int[] result = getBellmanFordPath(data, source);
        assertArrayEquals(new int[] { 0, 1, 2, 4 }, result);
    }

    @Test
    public void test2() {
        Integer[][] data = new Integer[][] {
                { 0, 1, 2, 0 },
                { 0, 0, 0, 3 },
                { 0, 0, 0, 4 },
                { 0, 0, 0, 0 } };
        int source = 1;
        int[] result = getBellmanFordPath(data, source);
        assertArrayEquals(new int[] { INF, 0, INF, 3 }, result);
    }

    @Test
    public void test3() {
        Integer[][] data = new Integer[][] {
                { 0, 1, 2, 0 },
                { 0, 0, 0, 3 },
                { 0, 0, 0, 4 },
                { 0, 0, 0, 0 } };
        int source = 2;
        int[] result = getBellmanFordPath(data, source);
        assertArrayEquals(new int[] { INF, INF, 0, 4 }, result);
    }

    @Test
    public void test4() {
        Integer[][] data = new Integer[][] {
                { 0, 1, 2, 0 },
                { 0, 0, 0, 3 },
                { 0, 0, 0, 4 },
                { 0, 0, 0, 0 } };
        int source = 3;

        int[] result = getBellmanFordPath(data, source);
        assertArrayEquals(new int[] { INF, INF, INF, 0 }, result);
    }
}
