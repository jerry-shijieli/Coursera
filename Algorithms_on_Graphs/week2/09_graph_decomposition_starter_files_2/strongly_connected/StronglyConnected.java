import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        //write your code here
        ArrayList<Integer>[] adj_R = (ArrayList<Integer>[])new ArrayList[adj.length]; // graph of reversing edges
        for (int i=0; i<adj.length; i++){
            adj_R[i] = new ArrayList<Integer>();
        }
        for (int i=0; i<adj.length; i++){
            for (int v: adj[i]){
                adj_R[v].add(i);
            }
        }
        Stack<Integer> postorder = new Stack<Integer>();
        boolean[] visited = new boolean[adj_R.length];
        for (int v=0; v<adj_R.length; v++) { // dfs over all nodes in G_R and get postorder
            if (!visited[v])
                explore(v, adj_R, visited, postorder);
        } 
        visited = new boolean[adj.length];
        int numOfSCC = 0;
        while (!postorder.isEmpty()){ // dfs over all nodes in G_R and get postorder
            int v = postorder.pop();
            if (!visited[v]){
                explore(v, adj, visited, null);
                numOfSCC++;
            }
        } 

        return numOfSCC;
    }

    private static void explore(int vertex, ArrayList<Integer>[] adj, boolean[] visited, Stack<Integer> postorder){
        visited[vertex] = true;
        for (int v: adj[vertex]){
            if (!visited[v])
                explore(v, adj, visited, postorder);
        }
        if (postorder != null)
            postorder.push(vertex);
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
        }
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}

