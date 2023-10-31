package com.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.example.PrimMSTConcurrent.Edge;
import com.example.PrimMSTConcurrent.Graph;
import org.junit.*;

public class PrimMSTConcurrentTest {
    @Test
    public void testMinimumSpanningTreeWithBasicGraph() {
        Graph graph = new Graph();

        // Create a basic graph
        List<Edge> graphEdges = new ArrayList<>();
        graphEdges.add(new Edge(0, 1, 10));
        graphEdges.add(new Edge(0, 2, 6));
        graphEdges.add(new Edge(0, 3, 5));
        graphEdges.add(new Edge(1, 3, 15));
        graphEdges.add(new Edge(2, 3, 4));

        for (Edge edge : graphEdges) {
            graph.addEdge(edge.from, edge.to, edge.weight);
        }

        int minWeight = PrimMSTConcurrent.parallelLLPPrim(graph);

        assertEquals(19, minWeight);
    }
}
