import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<Integer>();
        //write your code here
        int element = 1;
        subOptimalSummands(n, element, summands);
        return summands;
    }

    private static void subOptimalSummands(int n, int element, List<Integer> summands){
        if (n-element > element){
            summands.add(element);
            n -= element++;
            subOptimalSummands(n, element, summands);
        } else {
            element = n;
            n -= element;
            summands.add(element);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        // for (Integer summand : summands) {
        //     System.out.print(summand + " ");
        // }
        for (int i=0; i<summands.size(); i++){
            System.out.print(summands.get(i));
            if (i!=summands.size()-1)
                System.out.print(" ");
        }
    }
}

