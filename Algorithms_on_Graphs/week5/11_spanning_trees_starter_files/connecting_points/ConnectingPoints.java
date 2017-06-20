import java.util.Scanner;

public class ConnectingPoints {
    private static final double INF = Double.MAX_VALUE;

    private static double weight(int src, int dest, int[] x, int[] y){
        return Math.sqrt(Math.pow(x[dest]-x[src],2)+Math.pow(y[dest]-y[src],2));
    }

    private static boolean isQueueEmpty(boolean[] isInQueue){
        for (boolean x: isInQueue)
            if (x)
                return false;
        return true;
    }

    private static int extractMin(double[] cost, boolean[] isInQueue){
        int minIndex = -1;
        double minCost = INF;
        for (int i=0; i<cost.length; i++){
            if (isInQueue[i] && minCost>cost[i]){
                minCost = cost[i];
                minIndex = i;
            }
        }
        if (minIndex != -1)
            isInQueue[minIndex] = false;
        return minIndex;
    }

    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        //write your code here
        int numOfPoints = x.length;
        double[] cost = new double[numOfPoints];
        int[] prev = new int[numOfPoints]; // parent in MST
        boolean[] isInQueue = new boolean[numOfPoints]; // flag whether in Priority Queue measured by cost
        for (int i=0; i<numOfPoints; i++){
            cost[i] = INF;
            prev[i] = -1;
            isInQueue[i] = true;
        }
        cost[0] = 0;
        // apply Prims algorithm to obtain MST
        while (!isQueueEmpty(isInQueue)){
            int v = extractMin(cost, isInQueue);
            for (int z=0; z<numOfPoints; z++){
                if (z!=v && isInQueue[z]){
                    double w_vz = weight(v,z,x,y);
                    if (cost[z]>w_vz){
                        cost[z] = w_vz;
                        prev[z] = v;
                    }
                }
            }
        }
        // sum up distance among nodes in MST
        for (int i=0; i<numOfPoints; i++){
            result += cost[i];
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));
    }
}

