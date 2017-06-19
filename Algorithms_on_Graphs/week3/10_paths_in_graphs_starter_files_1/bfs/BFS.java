import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class BFS {
    private static class Node {
        int id;
        int dist;
        int prev;

        Node(int id, int dist, int prev){
            this.id = id;
            this.dist = dist;
            this.prev = prev;
        }
    }

    private static int extractMin(Node[] nodes, boolean[] isInQueue){
        int minDist = Integer.MAX_VALUE, minIndex = -1;
        for (int i=0; i<nodes.length; i++){
            if (isInQueue[i] && nodes[i].dist<minDist){
                minDist = nodes[i].dist;
                minIndex = nodes[i].id;
            }
        }
        if (minIndex != -1)
            isInQueue[minIndex] = false;
        return minIndex;
    }

    private static boolean isQueueEmpty(boolean[] isInQueue){
        for (boolean x: isInQueue)
            if (x)
                return false;
        return true;
    }

    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        //write your code here
        if ((s<0||s>=adj.length) || (t<0||t>=adj.length))
            return -1;
        Node[] nodes = new Node[adj.length];
        boolean[] isInQueue = new boolean[adj.length]; // flag whether Node object is in priority queue
        for (int i=0; i<adj.length; i++){
            nodes[i] = new Node(i, Integer.MAX_VALUE, -1);
            isInQueue[i] = true;
        }
        nodes[s].dist = 0;
        // Djikstra algorithm
        while (!isQueueEmpty(isInQueue)){
            int u = extractMin(nodes, isInQueue);
            if (u == -1) break;
            for (int v: adj[u]){
                if (nodes[v].dist > nodes[u].dist+1){
                    nodes[v].dist = nodes[u].dist + 1;
                    nodes[v].prev = u;
                }
            }
        }

        return nodes[t].dist==Integer.MAX_VALUE? -1: nodes[t].dist;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }
}

