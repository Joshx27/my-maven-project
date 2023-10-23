/*
 * package com.example;
 * 
 * import java.util.ArrayList;
 * import java.util.Arrays;
 * import java.util.List;
 * import java.util.concurrent.ForkJoinPool;
 * import java.util.concurrent.RecursiveTask;
 * 
 * class bellmanFordAlgorithm {
 * private int n; // Number of vertices
 * private List<Edge> edges;
 * private double[] distance;
 * private static final double INF = Double.POSITIVE_INFINITY;
 * 
 * public bellmanFordAlgorithm(int n, List<Edge> edges) {
 * this.n = n;
 * this.edges = edges;
 * distance = new double[n];
 * }
 * 
 * // initialize all distances to infinity, except the distance from the source
 * // node to itself
 * public void initialize(int source) {
 * for (int i = 0; i < n; i++) {
 * distance[i] = INF;
 * }
 * distance[source] = 0;
 * }
 * 
 * public void parallelLLPBellmanFord(int source) {
 * initialize(source);
 * int iterations = n - 1;
 * ForkJoinPool pool = new ForkJoinPool();
 * pool.invoke(new LLPBellmanFordTask(0, iterations));
 * 
 * if (hasNegativeCycle()) {
 * System.out.println("Negative cost cycle detected!");
 * }
 * }
 * 
 * public boolean hasNegativeCycle() {
 * for (Edge edge : edges) {
 * int u = edge.getSource();
 * int v = edge.getTarget();
 * double weight = edge.getWeight();
 * 
 * if (distance[u] != INF && distance[u] + weight < distance[v]) {
 * return true;
 * }
 * }
 * return false;
 * }
 * 
 * class LLPBellmanFordTask extends RecursiveTask<Void> {
 * private int start;
 * private int end;
 * 
 * public LLPBellmanFordTask(int start, int end) {
 * this.start = start;
 * this.end = end;
 * }
 * 
 * @Override
 * protected Void compute() {
 * if (end - start <= 1) {
 * // Sequentially relax edges
 * relaxEdges();
 * } else {
 * int mid = start + (end - start) / 2;
 * LLPBellmanFordTask left = new LLPBellmanFordTask(start, mid);
 * LLPBellmanFordTask right = new LLPBellmanFordTask(mid, end);
 * 
 * invokeAll(left, right);
 * 
 * // Merge results if needed
 * if (end - start > 2) {
 * relaxEdges();
 * }
 * }
 * return null;
 * }
 * }
 * 
 * public void relaxEdges() {
 * for (Edge edge : edges) {
 * int u = edge.getSource();
 * int v = edge.getTarget();
 * double weight = edge.getWeight();
 * 
 * if (distance[u] != INF && distance[u] + weight < distance[v]) {
 * distance[v] = distance[u] + weight;
 * }
 * }
 * }
 * 
 * public static void main(String[] args) {
 * int n = 5; // Number of vertices
 * List<Edge> edges = new ArrayList<>();
 * edges.add(new Edge(0, 1, -1));
 * edges.add(new Edge(0, 2, 4));
 * edges.add(new Edge(1, 2, 3));
 * edges.add(new Edge(1, 3, 2));
 * edges.add(new Edge(1, 4, 2));
 * edges.add(new Edge(3, 2, 5));
 * edges.add(new Edge(3, 1, 1));
 * edges.add(new Edge(4, 3, -3));
 * 
 * bellmanFordAlgorithm bf = new bellmanFordAlgorithm(n, edges);
 * bf.parallelLLPBellmanFord(0);
 * 
 * // Print the shortest distances
 * for (int i = 0; i < n; i++) {
 * System.out.println("Shortest distance from 0 to " + i + " is " +
 * bf.distance[i]);
 * }
 * }
 * }
 * 
 * class Edge {
 * private int source;
 * private int target;
 * private double weight;
 * 
 * public Edge(int source, int target, double weight) {
 * this.source = source;
 * this.target = target;
 * this.weight = weight;
 * }
 * 
 * public int getSource() {
 * return source;
 * }
 * 
 * public int getTarget() {
 * return target;
 * }
 * 
 * public double getWeight() {
 * return weight;
 * }
 * }
 */