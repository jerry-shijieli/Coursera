import java.util.*;

public class PrimitiveCalculator {
    private static final int INF = Integer.MAX_VALUE;

    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        int[] counts = new int[n+1];
        ////Arrays.fill(counts, INF);
        // while (n >= 1) {
        //     sequence.add(n);
        //     if (n % 3 == 0) {
        //         n /= 3;
        //     } else if (n % 2 == 0) {
        //         n /= 2;
        //     } else {
        //         n -= 1;
        //     }
        // }
        // Collections.reverse(sequence);
        for (int i=0; i<=n; i++){
            if (i>1){
                int t1, t2, t3;
                t1 = counts[i-1]+1;
                t2 = (i%2==0)? counts[i/2]+1: INF;
                t3 = (i%3==0)? counts[i/3]+1: INF;
                counts[i] = Math.min(t1, Math.min(t2, t3));
            }
        }
        // retrieve the solutions using backtracking
        sequence.add(n);
        int x = n;
        while (x>1) {
            int t1 = counts[x-1]+1;
            int t2 = (x%2==0)? counts[x/2]+1: INF;
            int t3 = (x%3==0)? counts[x/3]+1: INF;
            if (t1<=t2 && t1<=t3){
                x--;
            } else if (t2<t1 && t2<t3){
                x /= 2;
            } else {
                x /= 3;
            }
            sequence.add(x);
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}

