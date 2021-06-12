import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<Integer>();
        //write your code here
        int current = 1;
        recursiveOptimalSummands(n, current, summands);
        return summands;
    }

    private static void recursiveOptimalSummands(int rem, int cur, List<Integer> summands) {
        if (rem - cur > cur) {
            summands.add(cur);
            rem -= cur++;
            recursiveOptimalSummands(rem, cur, summands);
        } else {
            cur = rem;
            rem -= cur;
            summands.add(cur);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}

