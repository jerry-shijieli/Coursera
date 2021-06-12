import java.util.*;
import java.io.*;

public class CarFueling {
    static int computeMinRefills(int dist, int tank, int[] stops) {
        if (tank >= dist) {
            return 0;
        }

        final int n = stops.length;
        final int N = n + 2;
        int[] newStops = new int[N];
        for (int i=0; i<n; i++) {
            newStops[i + 1] = stops[i];
        }
        newStops[0] = 0;
        newStops[n + 1] = dist;

        int count = 0;
        int curIndex = 0, lastIndex = -1, remDist = dist;

        while (curIndex < N - 1) {
            lastIndex = curIndex;
            while (curIndex < N - 1 && newStops[curIndex + 1] - newStops[lastIndex] <= tank) {
                curIndex++;
            }

            if (curIndex == lastIndex) {
                return -1;
            }

            if (curIndex < N - 1) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int stops[] = new int[n];
        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        System.out.println(computeMinRefills(dist, tank, stops));
    }
}
