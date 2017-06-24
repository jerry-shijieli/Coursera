import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.lang.Math;
import java.util.HashSet;

public class DistWithCoords {
    private static class Impl {
        // Number of nodes
        int n;
        // Coordinates of nodes
        int[] x;
        int[] y;
        // See description of these fields in the starters for friend_suggestion
        ArrayList<Integer>[][] adj;
        ArrayList<Integer>[][] cost;
        Long[][] distance;
        // Long[][] estimate_dist; //
        ArrayList<PriorityQueue<Entry>> queue;
        // boolean[] visited;
        boolean[][] visited;
        ArrayList<Integer> workset;
        final Long INFINITY = Long.MAX_VALUE / 4;
        int[] destination;
        HashSet<Integer> visitedSet;

        Impl(int n) {
            this.n = n;
            // visited = new boolean[n];
            visited = new boolean[][]{new boolean[n], new boolean[n]};
            x = new int[n];
            y = new int[n];
            // Arrays.fill(visited, false);
            Arrays.fill(visited[0], false);
            Arrays.fill(visited[1], false);
            workset = new ArrayList<Integer>();
            distance = new Long[][] {new Long[n], new Long[n]};
            destination = new int[2];
            for (int i = 0; i < n; ++i) {
                distance[0][i] = distance[1][i] = INFINITY;
                // estimate_dist[0][i] = estimate_dist[1][i] = INFINITY;
            }
            queue = new ArrayList<PriorityQueue<Entry>>();
            queue.add(new PriorityQueue<Entry>(n));
            queue.add(new PriorityQueue<Entry>(n));
            visitedSet = new HashSet<Integer>(n); // keep all node modified in either forward or backward
        }

        // See the description of this method in the starters for friend_suggestion
        void clear() {
            for (int v : visitedSet) {
                distance[0][v] = distance[1][v] = INFINITY;
                // visited[v] = false;
                visited[0][v] = visited[1][v] = false;
            }
            // Arrays.fill(distance[0], INFINITY);
            // Arrays.fill(distance[1], INFINITY);
            // Arrays.fill(visited[0], false);
            // Arrays.fill(visited[1], false);
            destination = new int[2];
            visitedSet.clear();
            workset.clear();
            queue.get(0).clear();
            queue.get(1).clear();
        }

        // compute estimate distance using coordinates
        private Double estimate_dist(int side, int v){
            int dt = destination[side];
            int sr = destination[Math.abs(1-side)];
            double forward = Math.sqrt(Math.pow(x[dt]-x[v],2)+Math.pow(y[dt]-y[v],2));
            double backward = Math.sqrt(Math.pow(x[sr]-x[v],2)+Math.pow(y[sr]-y[v],2));
            return (forward-backward)/2;
        }

        // See the description of this method in the starters for friend_suggestion
        void visit(int side, int v, Long dist) {
            // Implement this method yourself
            if (distance[side][v] > dist){
                distance[side][v] = dist;
                queue.get(side).add(new Entry(dist+estimate_dist(side, v), v));
                visitedSet.add(v);
            }
        }

        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            clear();
            visit(0, s, 0L);
            visit(1, t, 0L);
            destination[0] = t;
            destination[1] = s;
            // Implement the rest of the algorithm yourself
            do {
                int side = 0;
                int u = process(side);
                if (u == -1)
                    break;
                if (u>=0 && visited[1][u])
                    return shortestDistance();

                side = 1;
                u = process(side);
                if (u == -1)
                    break;
                if (u>=0 && visited[0][u])
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
            Double cost; // total cost of both distance and estimate
            int node;
          
            public Entry(Double cost, int node)
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
        Impl DistWithCoords = new Impl(n);
        DistWithCoords.adj = (ArrayList<Integer>[][])new ArrayList[2][];
        DistWithCoords.cost = (ArrayList<Integer>[][])new ArrayList[2][];
        for (int side = 0; side < 2; ++side) {
            DistWithCoords.adj[side] = (ArrayList<Integer>[])new ArrayList[n];
            DistWithCoords.cost[side] = (ArrayList<Integer>[])new ArrayList[n];
            for (int i = 0; i < n; i++) {
                DistWithCoords.adj[side][i] = new ArrayList<Integer>();
                DistWithCoords.cost[side][i] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < n; i++) { 
            int x, y;
            x = in.nextInt();
            y = in.nextInt();
            DistWithCoords.x[i] = x;
            DistWithCoords.y[i] = y;
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            DistWithCoords.adj[0][x - 1].add(y - 1);
            DistWithCoords.cost[0][x - 1].add(c);
            DistWithCoords.adj[1][y - 1].add(x - 1);
            DistWithCoords.cost[1][y - 1].add(c);
        }

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            System.out.println(DistWithCoords.query(u-1, v-1));
        }
    }
}
