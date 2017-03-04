import java.util.*;

public class Knapsack {
    static int optimalWeight(int W, int[] w) {
        //write you code here
        int result = 0;
        // for (int i = 0; i < w.length; i++) {
        //   if (result + w[i] <= W) {
        //     result += w[i];
        //   }
        // }
        int[][] values = new int[w.length+1][W+1];
        for (int i=0; i<=w.length; i++){
            for (int j=0; j<=W; j++){
                if (i>0 && j>0){
                    values[i][j] = values[i-1][j];
                    if (j>=w[i-1]){
                        int val = values[i-1][j-w[i-1]] + w[i-1];
                        values[i][j] = Math.max(val, values[i][j]);
                    }
                }
            }
        }
        result = values[w.length][W];
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}

