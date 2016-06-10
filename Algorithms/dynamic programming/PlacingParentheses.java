import java.util.Scanner;

public class PlacingParentheses {
    private static long[][] minMat;
    private static long[][] maxMat;

    private static long getMaximValue(String exp) {
      //write your code here
        char[] expression = exp.toCharArray();
        int n = (expression.length + 1) / 2;
        char[] op = new char[n-1];
        int[] digits = new int[n];
        minMat = new long[n][n];
        maxMat = new long[n][n];
        for (int i=0; i<n; i++){
            digits[i] = Integer.parseInt(expression[i*2]+"");
            if (i != n-1)
                op[i] = expression[i*2+1];
            minMat[i][i] = digits[i];
            maxMat[i][i] = digits[i];
        }
        for (int s=0; s<n-1; s++){
            for (int i=0; i<n-s; i++){
                int j = s + i;
                MinAndMax(i, j, op);
            }
        }
      return maxMat[0][n-1];
    }

    private static void MinAndMax(int i, int j, char[] op){
        long minval = Long.MAX_VALUE;
        long maxval = Long.MIN_VALUE;
        for (int k=i; k<j; k++){
            long a = eval(maxMat[i][k], maxMat[k+1][j], op[k]);
            long b = eval(maxMat[i][k], minMat[k+1][j], op[k]);
            long c = eval(minMat[i][k], maxMat[k+1][j], op[k]);
            long d = eval(minMat[i][k], minMat[k+1][j], op[k]);
            minMat[i][j] = minfunc(minval, a, b, c, d);
            maxMat[i][j] = maxfunc(maxval, a, b, c, d);
        }
    }

    private static long minfunc(long r, long a, long b, long c, long d){
        return Math.min(r, Math.min(a, Math.min(b, Math.min(c, d))));
    }

    private static long maxfunc(long r, long a, long b, long c, long d){
        return Math.max(r, Math.max(a, Math.max(b, Math.max(c, d))));
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}

