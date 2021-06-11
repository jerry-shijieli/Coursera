import java.util.*;

public class FibonacciPartialSum {
    private static long[] signs;

    // failed to pass all cases
    private static long getFibonacciPartialSumNaive(long from, long to) {
        if (to <= 1) {
            return to;
        }

        long sum = 0;
        int period = (int) get_Pisano_period(10);
        signs = new long[period + 1];
        Arrays.fill(signs, -1);

        sum = from == to ? getFibonacciSumNaive(to) : (getFibonacciSumNaive(to) - getFibonacciSumNaive(from) + 10);

        return sum % 10;
    }

    private static long getFibonacciSumNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current  = 1;
        long sum      = 0;

        long period = get_Pisano_period(10);
        long period_sum = 0;

        for (long i=0; i<period; i++){
            int index = (int) (i % period);
            period_sum += fibonacci_module(index, 10);
        }
        period_sum %= 10;

        long nPeriod = n / period;

        for (long i=0; i<nPeriod; i++){
            sum += period_sum;
        }

        for (long i=period*nPeriod; i <= n; i++) {
            int index = (int) (i % period);
            sum += fibonacci_module(index, 10);
        }

        return (sum % 10);
    }

    private static long get_Pisano_period(long m){
        if (m<=1)
            return 0;
        long f0=0, f1=1, period=0;
        while(true){
            period++;
            long f2 = (f1 + f0) % m;
            f0 = f1;
            f1 = f2;
            if (f0==0 && f1==1)
                break;
        }
        return period;
    }

    private static long fibonacci_module(int index, long m){
        if (index <= 1)
            return (index % m);

        if (signs[index] >= 0){
            return signs[index];
        } else{
            long r0=0, r1=1, remainder=0;
            for (long i=2; i<=index; i++){
                remainder = (r0 + r1) % m;
                r0 = r1;
                r1 = remainder;
            }
            signs[index] = remainder;
            return remainder;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSumNaive(from, to));
    }
}

