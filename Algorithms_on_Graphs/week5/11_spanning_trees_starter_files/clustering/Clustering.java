import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

public class Clustering {
    private static class Edge {
        int src;
        int dest;
        double weight;

        Edge(int src, int dest, double weight){
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    private static double distance(int src, int dest, int[] x, int[] y){
        return Math.pow(x[dest]-x[src],2)+Math.pow(y[dest]-y[src], 2); // square of 2D distance
    }

    private static int findSet(int x, int[] parent){
        if (x != parent[x])
            x = parent[x];
        return x;
    }

    private static void unionSet(int root1, int root2, int[] parent, int[] rank){
        if (root1 == root2)
            return;
        int r1 = root1, r2=root2;
        if (rank[r1] > rank[r2])
            parent[r2] = r1;
        else if (rank[r1] < rank[r2])
            parent[r1] = r2;
        else {
            parent[r1] = r2;
            rank[r2]++;
        }
    }

    private static double clustering(int[] x, int[] y, int k) {
        //write your code here
        double inter_cluster_distance = Integer.MAX_VALUE;
        int numOfPoints = x.length;
        if (numOfPoints == 0) return 0;
        // put all edges into mini heap
        PriorityQueue<Edge> edge_queue = new PriorityQueue<Edge>(numOfPoints*(numOfPoints-1)/2, new Comparator<Edge>(){
            @Override 
            public int compare(Edge e1, Edge e2){
                if (e1.weight > e2.weight)
                    return 1;
                else if (e1.weight < e2.weight)
                    return -1;
                else
                    return 0;
            }
        });
        for (int u=0; u<numOfPoints; u++){
            for (int v=u+1; v<numOfPoints; v++){
                edge_queue.offer(new Edge(u, v, distance(u, v, x, y)));
            }
        }
        // initialize clusters (sets)
        int[] parent = new int[numOfPoints];    
        int[] rank = new int[numOfPoints];
        for (int u=0; u<numOfPoints; u++){
            parent[u] = u; // MakeSet
            rank[u] = 0;
        }
        // adopt Kruskal algorithm to merge nodes until only k sets left
        int setCount = numOfPoints;
        while (setCount > k && !edge_queue.isEmpty()){
            Edge tmp = edge_queue.poll();
            int set1 = findSet(tmp.src, parent);
            int set2 = findSet(tmp.dest, parent);
            if (set1 != set2){
                setCount--;
                unionSet(set1, set2, parent, rank);
            }
        }
        // find the smallest intercluster distance from remaining edges
        while (!edge_queue.isEmpty()){
            Edge tmp = edge_queue.poll();
            int set1 = findSet(tmp.src, parent);
            int set2 = findSet(tmp.dest, parent);
            if (set1 != set2){
                inter_cluster_distance = Math.sqrt(tmp.weight);
                break;
            }
        }
        
        return inter_cluster_distance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        System.out.println(clustering(x, y, k));
    }
}

