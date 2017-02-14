import java.util.*;

public class FibonacciSumLastDigit {
    private static long getFibonacciSumNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current  = 1;
        long sum      = 0;

        // for (long i = 0; i < n - 1; ++i) {
        //     long tmp_previous = previous;
        //     previous = current;
        //     current = (tmp_previous + current) % 10;
        //     sum += current % 10;
        // }
        long period = get_Pisano_period(10);
        for (long i=2; i<=n; i++){
            long index = n % period;
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

    private static long fibonacci_module(long index, long m){
        if (index <= 1)
            return (index % m);

        long r0=0, r1=1, remainder=0;
        for (long i=2; i<=index; i++){
            remainder = (r0 + r1) % m;
            r0 = r1;
            r1 = remainder;
        }
        return remainder;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long s = getFibonacciSumNaive(n);
        System.out.println(s);
    }
}

