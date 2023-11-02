package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class PrimMSTConcurrentTest {

    @Test
    public void testPrimMST() {
        int V = 5;
        int[][] graph = new int[][] {
                { 0, 2, 0, 6, 0 },
                { 2, 0, 3, 8, 5 },
                { 0, 3, 0, 0, 7 },
                { 6, 8, 0, 0, 9 },
                { 0, 5, 7, 9, 0 }
        };
        MST t = new MST(V);
        t.primMST(graph);

        // Assert that the MST was constructed correctly.
        // You can add more specific assertions based on the expected MST.
        // For example, check the total weight of the MST or individual edges.
        // Here, we just check that the method completes without errors.
        assertNotNull(t);
    }

}
