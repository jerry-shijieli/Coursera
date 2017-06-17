import java.util.ArrayList;
import java.util.Scanner;
// import java.util.Stack;

public class Acyclicity {
    private static int acyclic(ArrayList<Integer>[] adj) {
        //write your code here
        int size = adj.length;
        // Stack<Integer> nodes = new Stack<Integer>();
        boolean[] visited = new boolean[size];
        boolean[] linked = new boolean[size];
        for (int i=0; i<size; i++){
            if (explore(i, adj, visited, linked) == 1)
                return 1;
        }
        return 0;
    }

    private static int explore(int vertex, ArrayList<Integer>[] adj, boolean[] visited, boolean[] linked){
        if (!visited[vertex]){
            visited[vertex] = true;
            linked[vertex] = true;

            for (int v: adj[vertex]){
                if (!visited[v] && explore(v, adj, visited, linked)==1)
                    return 1;
                else if (linked[v])
                    return 1;
            }
        }
        linked[vertex] = false;
        return 0;
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
        System.out.println(acyclic(adj));
    }
}

