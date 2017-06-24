import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.HashSet;

public class FriendSuggestion {
    private static class Impl {
        // Number of nodes
        int n;
        // adj[0] and cost[0] store the initial graph, adj[1] and cost[1] store the reversed graph.
        // Each graph is stored as array of adjacency lists for each node. adj stores the edges,
        // and cost stores their costs.
        ArrayList<Integer>[][] adj;
        ArrayList<Integer>[][] cost;
        // distance[0] and distance[1] correspond to distance estimates in the forward and backward searches.
        Long[][] distance;
        // Two priority queues, one for forward and one for backward search.
        ArrayList<PriorityQueue<Entry>> queue;
        // visited[v] == true iff v was visited either by forward or backward search.
        // boolean[] visited;
        boolean[][] visited; // proc and procR
        // List of all the nodes which were visited either by forward or backward search.
        ArrayList<Integer> workset;
        final Long INFINITY = Long.MAX_VALUE / 4;
        HashSet<Integer> visitedSet;

        Impl(int n) {
            this.n = n;
            // visited = new boolean[n];
            visited = new boolean[][] {new boolean[n], new boolean[n]}; // 0 for visits in forward, and 1 for backward
            // Arrays.fill(visited, false);
            Arrays.fill(visited[0], false);
            Arrays.fill(visited[1], false);
            workset = new ArrayList<Integer>();
            distance = new Long[][] {new Long[n], new Long[n]};
            for (int i = 0; i < n; ++i) {
                distance[0][i] = distance[1][i] = INFINITY;
            }
            queue = new ArrayList<PriorityQueue<Entry>>();
            queue.add(new PriorityQueue<Entry>(n)); // 0 for forward
            queue.add(new PriorityQueue<Entry>(n)); // 1 for backward
            visitedSet = new HashSet<Integer>(n); // keep all node modified in either forward or backward
        }

        // Reinitialize the data structures before new query after the previous query
        void clear() {
            for (int v : visitedSet) {
                distance[0][v] = distance[1][v] = INFINITY;
                // visited[v] = false;
                visited[0][v] = visited[1][v] = false;
            }
            visitedSet.clear();
            workset.clear();
            queue.get(0).clear();
            queue.get(1).clear();
        }

        // Try to relax the distance from direction side to node v using value dist.
        void visit(int side, int v, Long dist) {
            // Implement this method yourself
            if (distance[side][v] > dist){
                distance[side][v] = dist;
                queue.get(side).add(new Entry(dist, v));
                visitedSet.add(v);
            }
        }

        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            clear();
            visit(0, s, 0L);
            visit(1, t, 0L);
            // Implement the rest of the algorithm yourself
            // queue.get(0).add(new Entry(0L, s));
            // queue.get(1).add(new Entry(0L, t));
            // apply bidirectional Dijkstra algorithm
            do {
                int side = 0;
                int u = process(side);
                if (u == -1)
                    break;
                if (u>=0 && visited[1][u]) // check if in procR
                    return shortestDistance();

                side = 1;
                u = process(side);
                if (u == -1)
                    break;
                if (u>=0 && visited[0][u]) // check if in proc
                    return shortestDistance();
            } while(true);
 
            return -1L;
        }

        // Process the node with relax operation and mark it, return node id
        private int process(int side){
            int u = -1;
            Entry tmp = null;
            if (queue.get(side).isEmpty())
                return -1;
            else{
                // extractMin(dist)
                while (!queue.get(side).isEmpty()){
                    tmp = queue.get(side).poll(); 
                    if (visited[side][tmp.node])
                        continue;
                    else {
                        u = tmp.node;
                        break;
                    }
                }
                if (u == -1)
                    return u;
                
                // process node u
                for (int i=0; i<adj[side][u].size(); i++){
                    int v = adj[side][u].get(i);
                    long w_uv = cost[side][u].get(i);
                    visit(side, v, distance[side][u]+w_uv);
                }
                visited[side][u] = true;
                workset.add(u);
            }
            return u;
        }

        private Long shortestDistance() {
            Long shortestDist = INFINITY;
            for (int u: workset){
                Long cur_dist = distance[0][u] + distance[1][u];
                if (shortestDist > cur_dist){
                    shortestDist = cur_dist;
                }
            }
            return shortestDist;
        }

        class Entry implements Comparable<Entry>
        {
            Long cost;
            int node;
          
            public Entry(Long cost, int node)
            {
                this.cost = cost;
                this.node = node;
            }
         
            public int compareTo(Entry other)
            {
                return cost < other.cost ? -1 : cost > other.cost ? 1 : 0;
            }
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Impl bidij = new Impl(n);
        bidij.adj = (ArrayList<Integer>[][])new ArrayList[2][];
        bidij.cost = (ArrayList<Integer>[][])new ArrayList[2][];
        for (int side = 0; side < 2; ++side) {
            bidij.adj[side] = (ArrayList<Integer>[])new ArrayList[n];
            bidij.cost[side] = (ArrayList<Integer>[])new ArrayList[n];
            for (int i = 0; i < n; i++) {
                bidij.adj[side][i] = new ArrayList<Integer>();
                bidij.cost[side][i] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            bidij.adj[0][x - 1].add(y - 1);
            bidij.cost[0][x - 1].add(c);
            bidij.adj[1][y - 1].add(x - 1);
            bidij.cost[1][y - 1].add(c);
        }

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            System.out.println(bidij.query(u-1, v-1));
        }
    }
}
