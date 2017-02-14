import java.util.*;

public class FibonacciPartialSum {
    private static long[] modules;

    private static long getFibonacciPartialSumNaive(long from, long to) {
        if (to <= 1)
            return to;

        long previous = 0;
        long current  = 1;

        int period = (int)get_Pisano_period(10);

        modules = new long[period+1];

        for (int i=0; i<=period; i++){
            modules[i] = -1;
        }

        // for (long i = 2; i <= from; ++i) {
        //     long index = i % period;
        //     current += fibonacci_module(index, 10);
        // }

        long sum = 0;


        for (long i = from; i <= to; ++i) {
            int index = (int)(i % period);
            sum += fibonacci_module(index, 10);
        }

        return sum % 10;
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

        if (modules[index] >= 0){
            return modules[index];
        } else{
            long r0=0, r1=1, remainder=0;
            for (long i=2; i<=index; i++){
                remainder = (r0 + r1) % m;
                r0 = r1;
                r1 = remainder;
            }
            modules[index] = remainder;
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

