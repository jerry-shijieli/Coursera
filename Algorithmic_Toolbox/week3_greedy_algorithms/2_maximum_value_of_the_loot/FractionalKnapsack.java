import java.util.Scanner;
import java.util.Arrays;

public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        //write your code here
        int n = values.length;
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(values[i], weights[i]);
        }

        Arrays.sort(items);

        int remWeight = capacity;
        for (int i = 0; i < n; i++) {
            if (remWeight == 0) {
                break;
            }
            if (items[i].getWeight() <= remWeight) {
                remWeight -= items[i].getWeight();
                value += items[i].getValue();
            } else {
                value += items[i].getTicker() * remWeight;
                remWeight = 0;
            }
        }

        return roundDouble(value, 4);
    }

    private static double roundDouble(double value, int decimals) {
        double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
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

    public static class Item implements Comparable<Item> {
        private int val;
        private int wgt; // positive
        private double vpw; // value per weight

        public Item(final int val, final int wgt) {
            this.val = val;
            this.wgt = wgt;
            this.vpw = (double) val / (double) wgt;
        }

        public double getTicker() {
            return vpw;
        }

        public int getWeight() {
            return wgt;
        }

        public int getValue() {
            return val;
        }

        public int compareTo(Item another) {
            return another.getTicker() < this.getTicker() ? -1 : 1;
        }
    }
}


