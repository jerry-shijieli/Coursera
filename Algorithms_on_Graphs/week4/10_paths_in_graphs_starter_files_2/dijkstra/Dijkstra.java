import java.util.*;

public class Dijkstra {
    private static final int INF = Integer.MAX_VALUE;

    private static class Node {
        int id;
        int dist;
        int prev;

        Node(int id, int dist, int prev){
            this.id = id;
            this.dist = dist; // distance from source
            this.prev = prev; // parent in shortest path
        }
    }

    private static int extractMin(Node[] nodes, boolean[] isInQueue){
        int minDist = INF;
        int minIndex = -1;
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

    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        if ((s<0||s>=adj.length) || (t<0||t>=adj.length))
            return -1;
        Node[] nodes = new Node[adj.length];
        boolean[] isInQueue = new boolean[adj.length]; // flag whether in priority queue ordered by Node dist
        for (int i=0; i<adj.length; i++){
            nodes[i] = new Node(i, INF, -1);
            isInQueue[i] = true;
        }
        nodes[s].dist = 0;
        while (!isQueueEmpty(isInQueue)){
            int u = extractMin(nodes, isInQueue);
            if (u==-1) break;
            for (int i=0; i<adj[u].size(); i++){
                int v = adj[u].get(i);
                int w_uv = cost[u].get(i);
                if (nodes[u].dist!=INF && nodes[v].dist>nodes[u].dist+w_uv){
                    nodes[v].dist = nodes[u].dist + w_uv;
                    nodes[v].prev = u;
                }
            }
        }

        return nodes[t].dist==INF? -1: nodes[t].dist;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }
}

