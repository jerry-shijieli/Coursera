import java.util.Scanner;
import java.util.Arrays;

public class FractionalKnapsack {
    // inner class for sort unit value and weight simultaneously.
    private static class Pair implements Comparable<Pair>{
        public int index;
        public double unit_val;
        public int weight;

        @Override
        public int compareTo(Pair p){
            double diff = (this.unit_val - p.unit_val);
            if (diff > 0)
                return 1;
            else if (diff == 0)
                return 0;
            else 
                return -1;
        }
    }

    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        //write your code here
        if (capacity == 0)
            return value;
        int rest_capacity = capacity;
        double[] unitValues = new double[values.length];
        Pair[] sorted_values = new Pair[values.length];
        for (int i=0; i<values.length; i++){
            if (weights[i] != 0)
                unitValues[i] = (double) values[i] / (double) weights[i];
            sorted_values[i] = new Pair();
            sorted_values[i].unit_val = unitValues[i];
            sorted_values[i].weight = weights[i];
            sorted_values[i].index = i;
        }
        Arrays.sort(sorted_values);
        for (int i=sorted_values.length-1; i>=0; i--){
            int w = Math.min(rest_capacity, sorted_values[i].weight); // take as much weight as possible
            value += w * sorted_values[i].unit_val; // update value
            sorted_values[i].weight -= w; // update remaining weight 
            rest_capacity -= w;
        }
        return value;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.println(getOptimalValue(capacity, values, weights));
    }
} 
