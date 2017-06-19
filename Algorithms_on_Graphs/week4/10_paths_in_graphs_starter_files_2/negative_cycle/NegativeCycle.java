import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle {
    private static final int INF = Integer.MAX_VALUE;
    
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        // write your code here
        int[] dist = new int[adj.length];
        int[] prev = new int[adj.length];
        for (int i=0; i<adj.length; i++){
            dist[i] = INF;
            prev[i] = -1;
        }
        dist[0] = 0;
        // apply Bellman-Ford algorithm (|V|-1) times for edge relaxing
        for (int count=0; count<adj.length-1; count++){
            for (int u=0; u<adj.length; u++){
                for (int i=0; i<adj[u].size(); i++){
                    int v = adj[u].get(i);
                    int w_uv = cost[u].get(i);
                    if (dist[u]!=INF && dist[v]>dist[u]+w_uv){
                        dist[v] = dist[u] + w_uv;
                        prev[v] = u;
                    }
                }
            }
        }
        // apply one more Bellman-Ford algorithm to detect negative cycle
        // ArrayList<Integer> lastRelaxedNodes = new ArrayList<Integer>();
        for (int u=0; u<adj.length; u++){
            for (int i=0; i<adj[u].size(); i++){
                int v = adj[u].get(i);
                int w_uv = cost[u].get(i);
                if (dist[u]!=INF && dist[v]>dist[u]+w_uv){
                    return 1;
                    // dist[v] = dist[u] + w_uv;
                    // prev[v] = u;
                    // lastRelaxedNodes.add(v);
                }
            }
        }

        return 0;
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
        System.out.println(negativeCycle(adj, cost));
    }
}

