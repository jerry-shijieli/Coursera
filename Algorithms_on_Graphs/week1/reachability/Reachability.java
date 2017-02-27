import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Reachability {
    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        //write your code here
        int result = 0;
        if (x<0 || x>=adj.length || y<0 || y>=adj.length) return result;
        boolean[] visited = new boolean[adj.length];
        Stack<Integer> nodes = new Stack<Integer>();
        nodes.push(x);
        visited[x] = true;
        while (!nodes.isEmpty()){
            int nd = nodes.pop();
            if (!adj[nd].isEmpty()){
                for (Integer i: adj[nd]){
                    if (i==y){
                        result = 1;
                        break;
                    }
                    if(!visited[i]){
                        nodes.push(i);
                        visited[i] = true;
                    }   
                }
            }
        }

        return result;
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
        System.out.println(reach(adj, x, y));
    }
}

