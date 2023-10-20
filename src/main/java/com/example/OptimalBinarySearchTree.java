package com.example;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class OptimalBinarySearchTree {

    static class Node {
        int key;
        Node left, right;

        public Node(int key) {
            this.key = key;
            left = right = null;
        }
    }

    static class InsertTask extends RecursiveAction {
        Node root;
        int key;

        public InsertTask(Node root, int key) {
            this.root = root;
            this.key = key;
        }

        @Override
        protected void compute() {
            if (key < root.key) {
                if (root.left == null) {
                    root.left = new Node(key);
                } else {
                    invokeAll(new InsertTask(root.left, key));
                }
            } else if (key > root.key) {
                if (root.right == null) {
                    root.right = new Node(key);
                } else {
                    invokeAll(new InsertTask(root.right, key));
                }
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        Node root = new Node(10);

        int[] keys = { 5, 15, 3, 7, 12, 18, 1, 9 };

        InsertTask task = new InsertTask(root, keys[0]);
        pool.invoke(task);

        for (int i = 1; i < keys.length; i++) {
            pool.invoke(new InsertTask(root, keys[i]));
        }

        // Printing the tree to verify the insertion
        System.out.println("In-order Traversal:");
        inOrderTraversal(root);
    }

    static void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.key + " ");
            inOrderTraversal(root.right);
        }
    }
}
