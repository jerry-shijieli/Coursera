import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
    private static final int UNKNOWN = 0;
    private static final int BLACK = -1;
    private static final int WHITE = 1;

    private static class Node {
        int id;
        int color; // flag of parts in bipartite graph

        Node(int id){
            this.id = id;
            this.color = UNKNOWN;
        }

        Node(int id, int color){
            this.id = id;
            this.color = color;
        }
    }

    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
        if (adj==null || adj.length==0)
            return -1;
        Node[] nodes = new Node[adj.length];
        for (int i=0; i<adj.length; i++){
            nodes[i] = new Node(i);
        }
        nodes[0].color = BLACK;
        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(nodes[0]);
        while (!queue.isEmpty()){
            Node tmp = queue.poll();
            int u = tmp.id;
            int colorX = tmp.color; // color(part) of this end
            int colorY = colorX==BLACK? WHITE: BLACK; // supposed color of other end
            for (int v: adj[u]){
                if (nodes[v].color == UNKNOWN){
                    nodes[v].color = colorY;
                    queue.offer(nodes[v]);
                } else {
                    if (nodes[v].color == colorX)
                        return 0;
                }
            }
        }

        return 1;
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
        System.out.println(bipartite(adj));
    }
}

