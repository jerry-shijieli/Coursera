import java.util.*;

public class FibonacciHuge {
    private static long getFibonacciHugeNaive(long n, long m) {
        long period = get_Pisano_period(m);
        long index = n % period;
        return fibonacci_module(index, m);
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
        long m = scanner.nextLong();
        System.out.println(getFibonacciHugeNaive(n, m));
    }
}

