import java.util.*;

public class ShortestPaths {
    private static final long INF = Long.MAX_VALUE;

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, long[] distance, int[] reachable, int[] shortest) {
      //write your code here
        int[] prev = new int[adj.length];
        for (int i=0; i<adj.length; i++){
            prev[i] = -1;
        }
        distance[s] = 0;
        reachable[s] = 1;
        // |V|-1 iterations of Bellman-Ford algorithm for full edge relaxation
        for (int count=0; count<adj.length-1; count++){
            for (int u=0; u<adj.length; u++){
                for (int i=0; i<adj[u].size(); i++){
                    int v = adj[u].get(i);
                    int w_uv = cost[u].get(i);
                    if (distance[u]!=INF && distance[v]>distance[u]+w_uv){
                        distance[v] = distance[u] + w_uv;
                        prev[v] = u;
                        reachable[v] = 1;
                    }
                }
            }
        }
        // |V|th iteration of Bellman-Ford to detect links to negative cycle.
        ArrayList<Integer> lastRelaxedNode = new ArrayList<Integer>();
        for (int u=0; u<adj.length; u++){
            for (int i=0; i<adj[u].size(); i++){
                int v = adj[u].get(i);
                int w_uv = cost[u].get(i);
                if (distance[u]!=INF && distance[v]>distance[u]+w_uv){
                    distance[v] = distance[u] + w_uv;
                    prev[v] = u;
                    reachable[v] = 1;
                    lastRelaxedNode.add(v);
                }
            }
        }
        // Retrieve nodes in negative cycle
        for (int v: lastRelaxedNode){
            int x = v;
            for (int count=0; count<adj.length; count++){
                x = prev[x];
            }
            int y = x;
            x = prev[x];
            while (x != y){
                shortest[x] = 0;
                x = prev[x];
            }
            shortest[y] = 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        int s = scanner.nextInt() - 1;
        long distance[] = new long[n];
        int reachable[] = new int[n];
        int shortest[] = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Long.MAX_VALUE;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adj, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }

}

